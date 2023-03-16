package com.batro.ui.screen.editTransaction.coin

import com.airbnb.mvrx.*
import com.batro.data.model.PortfolioCoinTransaction
import com.batro.data.model.PortfolioTransaction
import com.batro.data.repository.portfolioTransaction.PortfolioTransactionRepository
import com.batro.ui.model.PortfolioItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.Serializable


data class EditCoinTransactionArgument(
    val portfolioCoinItemId: Int
) : Serializable


@Suppress("unused")
data class EditCoinTransactionState(
    val portfolioCoinItemId: Int,
    val portInfo: PortfolioItem? = null,
    val portfolioCoinTransaction: PortfolioCoinTransaction? = null
) : MavericksState {
    constructor(args: EditCoinTransactionArgument) : this(args.portfolioCoinItemId)
}


class EditCoinTransactionViewModel(initialState: EditCoinTransactionState) :
    MavericksViewModel<EditCoinTransactionState>(initialState), KoinComponent {

    private val portfolioTransactionRepository: PortfolioTransactionRepository by inject()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val tx = portfolioTransactionRepository.getCoinTransactionById(initialState.portfolioCoinItemId)
            setState { copy(portfolioCoinTransaction = tx) }
        }
    }

    fun updateTransaction(tx: PortfolioTransaction) {
        portfolioTransactionRepository.updatePortfolioTransaction(tx)
    }

    fun deleteTransaction() {
        withState { state ->
            portfolioTransactionRepository.deletePortfolioCoinTransactionById(state.portfolioCoinItemId)
        }
    }

}
