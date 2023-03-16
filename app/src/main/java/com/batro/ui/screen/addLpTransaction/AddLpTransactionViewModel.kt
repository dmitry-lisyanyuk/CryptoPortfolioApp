package com.batro.ui.screen.addLpTransaction

import com.airbnb.mvrx.*
import com.batro.data.model.LPToken
import com.batro.data.model.Platform
import com.batro.data.model.PortfolioTransaction
import com.batro.domain.usecase.GetLPTokenByContractAddressUseCase
import com.batro.domain.usecase.platform.GetPlatformByIdUseCase
import com.batro.domain.usecase.portfolio.AddPortfolioTransactionUseCase
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.Serializable


data class AddTransactionLpAddressArgument(
    val lpTokenAddress: String?
) : Serializable

data class AddLpTransactionState(
    val lpTokenAddress: String? = null,
    val lpToken: LPToken? = null,
    val platform: Platform? = null,
) : MavericksState {

    @Suppress("unused")
    constructor(args: AddTransactionLpAddressArgument) : this(lpTokenAddress = args.lpTokenAddress)
}


class AddLpTransactionViewModel(
    initialState: AddLpTransactionState
) : MavericksViewModel<AddLpTransactionState>(initialState), KoinComponent {

    private val getPlatformUseCase: GetPlatformByIdUseCase by inject()

    private val getLPTokenUseCase: GetLPTokenByContractAddressUseCase by inject()

    private val addPortfolioTransactionUseCase: AddPortfolioTransactionUseCase by inject()


    init {
        if (initialState.lpTokenAddress != null) {
            viewModelScope.launch {
                val lp = getLPTokenUseCase(initialState.lpTokenAddress)
                val platform = getPlatformUseCase(lp.chainId)
                setState { copy(lpToken = lp, platform = platform) }
            }
        }
    }

    fun setPlatform(platform: Platform) {
        setState {
            val lp = if (lpToken?.chainId == platform.id) {
                lpToken
            } else null
            copy(platform = platform, lpToken = lp)
        }
    }

    fun setLPToken(lpToken: LPToken) {
        setState { copy(lpToken = lpToken) }
    }

    fun addPortfolioInfo(portfolioTransaction: PortfolioTransaction) {
        addPortfolioTransactionUseCase(portfolioTransaction)
    }
}
