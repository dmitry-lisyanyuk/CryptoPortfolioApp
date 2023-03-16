package com.batro.ui.screen.delailsPorfolio.coin

import androidx.compose.runtime.Immutable
import com.airbnb.mvrx.*
import com.batro.data.model.Coin
import com.batro.data.model.PortfolioCoinTransaction
import com.batro.data.repository.portfolioTransaction.PortfolioTransactionRepository
import com.batro.ui.model.PortfolioCoinItem
import com.batro.ui.model.PortfolioItem
import com.batro.domain.usecase.GetCoinMarketChartUseCase
import com.batro.domain.usecase.GetCoinUseCase
import com.batro.domain.usecase.ObserveCoinPriceUseCase
import com.batro.ui.model.PriceSnapshot
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.web3j.utils.Convert
import java.io.Serializable
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode


@Immutable
data class DetailsCoinInfo(
    val totalAmount: String,
    val totalWorth: String,
    val avgPrice: String,
    val currentPrice: String,
    val txList: List<PortfolioItem>
)

data class DetailsIdArgument(
    val coinId: String
) : Serializable

data class DetailsCoinState(
    val coinId: String,
    val coinRequest: Async<Coin> = Uninitialized,
    val detailsCoinInfo: DetailsCoinInfo? = null,
    val price: Async<BigDecimal> = Uninitialized,
    val transactionList: List<PortfolioCoinTransaction>? = null,
    val marketChartRequest: Async<List<PriceSnapshot>> = Uninitialized
) : MavericksState {

    @Suppress("unused")
    constructor(args: DetailsIdArgument) : this(args.coinId)
}

class DetailsCoinViewModel(initialState: DetailsCoinState) :
    MavericksViewModel<DetailsCoinState>(initialState), KoinComponent {

    private val observeCoinPriceUseCase: ObserveCoinPriceUseCase by inject()
    private val portfolioTransactionRepository: PortfolioTransactionRepository by inject()

    private val getCoinMarketChartUseCase: GetCoinMarketChartUseCase by inject()
    private val getCoinUseCase: GetCoinUseCase by inject()

    init {

        suspend {
            getCoinUseCase(initialState.coinId)
        }.execute { copy(coinRequest = it) }

        portfolioTransactionRepository.portfolioCoinTransactionFlowByCoinId(initialState.coinId)
            .onEach { txList ->
                setState { copy(transactionList = txList) }
            }.launchIn(viewModelScope)

        observeCoinPriceUseCase(initialState.coinId)
            .execute { copy(price = it) }

        suspend {
            getCoinMarketChartUseCase(initialState.coinId)
        }.execute { copy(marketChartRequest = it) }

        onEach(
            DetailsCoinState::transactionList,
            DetailsCoinState::price
        ) { transactionList, price ->
            transactionList ?: return@onEach
            if (transactionList.isEmpty()) {
                val detailInfo = DetailsCoinInfo("", "", "", "", listOf())
                setState { copy(detailsCoinInfo = detailInfo) }
                return@onEach
            }

            val currentPrice = price.invoke() ?: return@onEach
            val itemList = transactionList.map { transaction ->
                val totalPriceUSD = transaction.amount.multiply(currentPrice)
                    .multiply(BigDecimal.valueOf(100))
                    .setScale(0, RoundingMode.HALF_DOWN).longValueExact()
                val initialPriceUSD = transaction.amount.multiply(transaction.price)
                    .multiply(BigDecimal.valueOf(100))
                    .setScale(0, RoundingMode.HALF_DOWN).longValueExact()
                PortfolioCoinItem(
                    transaction.coin,
                    Convert.toWei(transaction.amount, Convert.Unit.ETHER).toBigInteger(),
                    totalPriceUSD,
                    initialPriceUSD,
                    transaction.price,
                    transaction.note,
                    transaction.coinInfoId!!
                )
            }

            val totalAmount = transactionList.sumOf { it.amount }
            val totalWorth = totalAmount.multiply(currentPrice)
            val totalPaid = transactionList.sumOf { it.price.multiply(it.amount) }
            val avgPrice = totalPaid.divide(totalAmount, MathContext.DECIMAL128)
                .setScale(2, RoundingMode.DOWN)

            val detailInfo = DetailsCoinInfo(
                totalAmount = totalAmount.toPlainString(),
                totalWorth = "$" + totalWorth.toPlainString(),
                avgPrice = "$" + avgPrice.toPlainString(),
                currentPrice = "$" + currentPrice.toPlainString(),
                itemList
            )
            setState { copy(detailsCoinInfo = detailInfo) }
        }
    }
}
