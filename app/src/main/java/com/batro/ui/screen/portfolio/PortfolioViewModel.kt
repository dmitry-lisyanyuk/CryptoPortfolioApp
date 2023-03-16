package com.batro.ui.screen.portfolio

import android.annotation.SuppressLint
import com.airbnb.mvrx.*
import com.batro.ui.model.PortfolioItem
import com.batro.data.model.*
import com.batro.domain.usecase.GetPortfolioTotalUseCase
import com.batro.domain.usecase.ObservePortfolioItemsUseCase
import com.batro.ui.model.PortfolioTotal
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

data class PortfolioState(
    val portfolioTransactionList: List<PortfolioTransaction>? = null,
    val portfolioItemList: Async<List<PortfolioItem>> = Loading(),
    val totalProfit: PortfolioTotal? = null
) : MavericksState

@SuppressLint("CheckResult")
class PortfolioViewModel(
    initialState: PortfolioState
) : MavericksViewModel<PortfolioState>(initialState), KoinComponent {

    private val observePortfolioItemsUseCase: ObservePortfolioItemsUseCase by inject()

    private val getPortfolioTotalUseCase: GetPortfolioTotalUseCase by inject()

    init {
        observePortfolioItemsUseCase()
            .onEach { portfolioItems ->
                setState {
                    copy(
                        portfolioItemList = Success(portfolioItems),
                        totalProfit = getPortfolioTotalUseCase(portfolioItems)
                    )
                }
            }.retryWhen { cause, _ ->
                setState {
                    copy(
                        portfolioItemList = Fail(cause, portfolioItemList.invoke()),
                        totalProfit = getPortfolioTotalUseCase(portfolioItemList.invoke())
                    )
                }
                delay(7000)
                true
            }.launchIn(viewModelScope)
    }

}