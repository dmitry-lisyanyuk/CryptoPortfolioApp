package com.batro.ui.screen.selectLpToken

import com.airbnb.mvrx.*
import com.batro.data.model.LPToken
import com.batro.domain.usecase.GetLiquidityPoolsUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.Serializable

data class SelectLpTokenArgument(
    val platformId: String
) : Serializable

data class SelectLpTokenState(
    val platformId: String,
    val request: Async<List<LPToken>> = Uninitialized
) : MavericksState {
    @Suppress("unused")
    constructor(args: SelectLpTokenArgument) : this(args.platformId)
}

class SelectLpTokenViewModel(
    initialState: SelectLpTokenState
) : MavericksViewModel<SelectLpTokenState>(initialState), KoinComponent {

    private val getLiquidityPoolsUseCase: GetLiquidityPoolsUseCase by inject()

    init {
        suspend {
            getLiquidityPoolsUseCase(initialState.platformId)
        }.execute(retainValue = SelectLpTokenState::request) { copy(request = it) }
    }

}