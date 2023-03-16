package com.batro.ui.screen.editTransaction.lp

import com.airbnb.mvrx.*
import com.batro.data.model.*
import com.batro.data.repository.portfolioTransaction.PortfolioTransactionRepository
import com.batro.domain.usecase.platform.GetPlatformByIdUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.Serializable


data class EditLpTransactionArgument(
    val portfolioLpItemId: Int
) : Serializable


@Suppress("unused")
data class EditLpTransactionState(
    val portfolioLpItemId: Int,
    val platform: Platform? = null,
    val portfolioLpTransaction: PortfolioLpTransaction? = null
) : MavericksState {
    constructor(args: EditLpTransactionArgument) : this(args.portfolioLpItemId)
}


class EditLpTransactionViewModel(initialState: EditLpTransactionState) :
    MavericksViewModel<EditLpTransactionState>(initialState), KoinComponent {


    private val portfolioTransactionRepository: PortfolioTransactionRepository by inject()

    private val getPlatformUseCase: GetPlatformByIdUseCase by inject()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val portfolioLpTransaction = portfolioTransactionRepository.getLpTransactionById(
                initialState.portfolioLpItemId
            )
            val chain = getPlatformUseCase(portfolioLpTransaction.lpToken.chainId)
            setState {
                copy(platform = chain, portfolioLpTransaction = portfolioLpTransaction)
            }
        }
    }

    fun updatePortfolioInfo(portfolioTransaction: PortfolioTransaction) {
        portfolioTransactionRepository.updatePortfolioTransaction(portfolioTransaction)
    }

    fun deleteLpTransaction() {
        withState { state ->
            portfolioTransactionRepository.deletePortfolioLpTransactionById(state.portfolioLpItemId)
        }
    }
}
