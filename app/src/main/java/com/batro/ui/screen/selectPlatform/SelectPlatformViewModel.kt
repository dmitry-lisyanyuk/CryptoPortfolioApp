package com.batro.ui.screen.selectPlatform

import com.airbnb.mvrx.*
import com.batro.data.model.Platform
import com.batro.domain.usecase.platform.GetPlatformListUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

data class SelectPlatformState(
    val request: Async<List<Platform>> = Uninitialized
) : MavericksState

class SelectPlatformViewModel(
    initialState: SelectPlatformState
) : MavericksViewModel<SelectPlatformState>(initialState), KoinComponent {

    private val getPlatformsUseCase: GetPlatformListUseCase by inject()

    init {
        suspend {
            getPlatformsUseCase()
        }.execute(retainValue = SelectPlatformState::request) { copy(request = it) }
    }

}