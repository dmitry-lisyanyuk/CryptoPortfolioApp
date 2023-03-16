package com.batro.ui.screen.addCoinTransaction

import com.airbnb.mvrx.*
import com.batro.data.model.Coin
import com.batro.data.model.PortfolioTransaction
import com.batro.domain.usecase.GetCoinUseCase
import com.batro.domain.usecase.portfolio.AddPortfolioTransactionUseCase
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.Serializable

data class CurrencyAddIdArgument(
    val coinId: String?
) : Serializable


@Suppress("unused")
data class AddCoinTransactionState(
    val requestCoinId: String? = null,
    val coin: Coin? = null
) : MavericksState {
    constructor(args: CurrencyAddIdArgument) : this(requestCoinId = args.coinId)
}

class AddCoinTransactionViewModel(
    initialState: AddCoinTransactionState
) : MavericksViewModel<AddCoinTransactionState>(initialState), KoinComponent {

    private val getCoinUseCase: GetCoinUseCase by inject()

    private val addPortfolioTransactionUseCase: AddPortfolioTransactionUseCase by inject()

    init {
        if (initialState.requestCoinId != null) {
            viewModelScope.launch {
                val coin = getCoinUseCase(initialState.requestCoinId)
                setState { copy(coin = coin) }
            }
        }
    }

    fun setCurrency(coin: Coin) {
        setState { copy(coin = coin) }
    }

    fun addPortfolioInfo(portfolioTransaction: PortfolioTransaction) {
        addPortfolioTransactionUseCase(portfolioTransaction)
    }

}
