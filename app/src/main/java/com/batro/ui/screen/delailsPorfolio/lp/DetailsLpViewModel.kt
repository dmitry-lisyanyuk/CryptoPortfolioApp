package com.batro.ui.screen.delailsPorfolio.lp

import androidx.compose.runtime.Immutable
import com.airbnb.mvrx.*
import com.batro.data.model.*
import com.batro.data.repository.portfolioTransaction.PortfolioTransactionRepository
import com.batro.data.repository.lpTokenSupply.LPTokenSupplyRepository
import com.batro.domain.usecase.CalcPortfolioLPItemUseCase
import com.batro.domain.usecase.GetLPTokenByContractAddressUseCase
import com.batro.domain.usecase.ObserveCoinListPriceUseCase
import com.batro.ui.model.PortfolioItem
import com.batro.ui.model.PortfolioLPItem
import com.batro.util.extension.longAmountToString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.Serializable
import java.math.BigDecimal

val EmptyDetailInfo = DetailsLpInfo("", "", "", "", listOf())

data class DetailsLpInfo(
    val totalLpAmount: String,
    val totalWorth: String,
    val total2: String,
    val currentPrice: String,
    val txList: List<PortfolioItem>
)

data class DetailsLpAddressArgument(
    val lpAddress: String
) : Serializable

@Immutable
data class DetailsLpState(
    val lpTokenAddress: String,
    val lpTokenRequest: Async<LPToken> = Uninitialized,
    val detailsLpInfo: DetailsLpInfo? = null
) : MavericksState {

    @Suppress("unused")
    constructor(args: DetailsLpAddressArgument) : this(args.lpAddress)
}

@Suppress("EXPERIMENTAL_IS_NOT_ENABLED")
@OptIn(ExperimentalCoroutinesApi::class)
class DetailsLpViewModel(initialState: DetailsLpState) :
    MavericksViewModel<DetailsLpState>(initialState), KoinComponent {

    private val observeCoinListPriceUseCase: ObserveCoinListPriceUseCase by inject()

    private val portfolioTransactionRepository: PortfolioTransactionRepository by inject()

    private val lpTokenSupplyRepository: LPTokenSupplyRepository by inject()

    private val getLPTokenUseCase: GetLPTokenByContractAddressUseCase by inject()
    private val calcPortfolioLPItemUseCase: CalcPortfolioLPItemUseCase by inject()


    init {
        val txFlow = portfolioTransactionRepository.portfolioLpTransactionFlowByLpContractAddress(
            initialState.lpTokenAddress
        )
        val priceFlow = flow {
            val lpToken = getLPTokenUseCase(initialState.lpTokenAddress)
            setState { copy(lpTokenRequest = Success(lpToken)) }
            emit(lpToken)
        }.flatMapLatest { lpToken ->
            observeCoinListPriceUseCase(
                setOf(
                    lpToken.coin0.coingeckoId,
                    lpToken.coin1.coingeckoId
                )
            )
        }

        txFlow.combine(priceFlow) { a, b ->
            a to b
        }.flatMapLatest {
            lpFlow(it.first, it.second)
        }.onEach { detailInfo ->
            setState { copy(detailsLpInfo = detailInfo) }
        }.flowOn(Dispatchers.IO).launchIn(viewModelScope)

    }

    private fun lpFlow(
        portfolioLpInfoList: List<PortfolioLpTransaction>,
        prices: Map<String, BigDecimal>
    ): Flow<DetailsLpInfo> {
        return if (portfolioLpInfoList.isNotEmpty()) {
            lpTokenSupplyRepository.lpTokenSupplyFlow().mapNotNull { tokenSupplyList ->
                val dataList = mutableListOf<PortfolioLPItem>()
                val lpToken = portfolioLpInfoList.first().lpToken
                val supply = tokenSupplyList.find { it.contractAddress == lpToken.contractAddress }
                    ?: return@mapNotNull null
                portfolioLpInfoList.forEach { entry ->
                    val item = calcPortfolioLPItemUseCase(
                        lpToken,
                        supply,
                        listOf(entry),
                        prices,
                        entry.coinInfoId ?: 0
                    )
                    dataList.add(item)
                }
                val totalLpAmount = portfolioLpInfoList.sumOf { it.lpAmount }
                val totalWorth = dataList.sumOf { it.lpPriceUSD }
                DetailsLpInfo(
                    totalLpAmount = totalLpAmount.toPlainString(),
                    totalWorth = "$" + totalWorth.longAmountToString(),
                    "",
                    "",
                    dataList
                )
            }
        } else flowOf(EmptyDetailInfo)
    }

}
