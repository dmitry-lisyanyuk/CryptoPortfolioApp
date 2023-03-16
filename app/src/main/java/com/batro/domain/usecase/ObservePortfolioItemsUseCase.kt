@file:OptIn(ExperimentalCoroutinesApi::class)

package com.batro.domain.usecase

import com.batro.ui.model.PortfolioCoinItem
import com.batro.ui.model.PortfolioItem
import com.batro.ui.model.PortfolioLPItem
import com.batro.data.model.PortfolioCoinTransaction
import com.batro.data.model.PortfolioLpTransaction
import com.batro.data.model.PortfolioTransaction
import com.batro.data.repository.portfolioTransaction.PortfolioTransactionRepository
import com.batro.data.repository.lpTokenSupply.LPTokenSupplyRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import org.web3j.utils.Convert
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.absoluteValue

class ObservePortfolioItemsUseCase(
    private val portfolioTransactionRepository: PortfolioTransactionRepository,
    private val lpTokenSupplyRepository: LPTokenSupplyRepository,
    private val observeCoinListPriceUseCase: ObserveCoinListPriceUseCase,
    private val calcPortfolioLPItemUseCase: CalcPortfolioLPItemUseCase,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    operator fun invoke(): Flow<List<PortfolioItem>> {
        return portfolioTransactionRepository.portfolioTransactionFlow()
            .flatMapLatest { transactions ->
                if (transactions.isEmpty()) {
                    flowOf(listOf())
                } else {
                    val currencies = transactions.flatMap { info ->
                        when (info) {
                            is PortfolioLpTransaction -> {
                                listOf(
                                    info.lpToken.coin0.coingeckoId,
                                    info.lpToken.coin1.coingeckoId
                                )
                            }
                            is PortfolioCoinTransaction -> {
                                listOf(info.coin.coingeckoId)
                            }
                        }
                    }.toSet()
                    observeCoinListPriceUseCase(currencies)
                        .flatMapLatest { prices ->
                            val coinFlow = coinFlow(transactions, prices)
                            val lpFlow = lpFlow(transactions, prices)
                            coinFlow.combine(lpFlow) { coinList, lpList ->
                                coinList.plus(lpList).sortedByDescending {
                                    when (it) {
                                        is PortfolioCoinItem -> it.totalPriceUSD
                                        is PortfolioLPItem -> it.lpPriceUSD
                                    }
                                }
                            }
                        }
                }
            }.distinctUntilChanged().flowOn(ioDispatcher)
    }

    private fun coinFlow(
        portfolioInfoList: List<PortfolioTransaction>,
        prices: Map<String, BigDecimal>
    ): Flow<List<PortfolioItem>> {
        val dataList = portfolioInfoList.filterIsInstance(PortfolioCoinTransaction::class.java)
            .groupBy { it.coin.coingeckoId }
            .map { group ->
                val coin = group.value.first()
                val amountSum = group.value.sumOf { it.amount }

                val price = prices[coin.coin.coingeckoId]!!

                val totalPriceUSD = amountSum.multiply(price)
                    .multiply(BigDecimal.valueOf(100))
                    .setScale(0, RoundingMode.HALF_DOWN).longValueExact()

                val initialPriceUSD = group.value.sumOf { it.amount.multiply(it.price) }
                    .multiply(BigDecimal.valueOf(100))
                    .setScale(0, RoundingMode.HALF_DOWN).longValueExact()

                PortfolioCoinItem(
                    coin.coin,
                    Convert.toWei(amountSum, Convert.Unit.ETHER).toBigInteger(),
                    totalPriceUSD,
                    initialPriceUSD,
                    price,
                    null,
                    coin.coin.coingeckoId.hashCode().absoluteValue
                )
            }
        return flowOf(dataList)
    }

    private fun lpFlow(
        portfolioInfoList: List<PortfolioTransaction>,
        prices: Map<String, BigDecimal>
    ): Flow<List<PortfolioItem>> {
        val portfolioLpInfoList =
            portfolioInfoList.filterIsInstance(PortfolioLpTransaction::class.java)
        return if (portfolioLpInfoList.isNotEmpty()) {
            lpTokenSupplyRepository.lpTokenSupplyFlow().map { tokenSupplyList ->
                val dataList = mutableListOf<PortfolioItem>()
                portfolioLpInfoList.groupBy { it.lpToken.contractAddress }.entries.forEach { entry ->
                    val lpToken = entry.value.first().lpToken
                    val supply =
                        tokenSupplyList.find { it.contractAddress == entry.key } ?: return@forEach
                    val lpTransactions = entry.value
                    val item = calcPortfolioLPItemUseCase(
                        lpToken,
                        supply,
                        lpTransactions,
                        prices,
                        lpToken.contractAddress.hashCode().absoluteValue
                    )
                    dataList.add(item)
                }
                dataList
            }
        } else flowOf(listOf())
    }
}