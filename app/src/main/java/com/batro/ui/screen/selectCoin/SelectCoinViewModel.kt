package com.batro.ui.screen.selectCoin

import com.airbnb.mvrx.*
import com.batro.data.model.Coin
import com.batro.domain.usecase.GetCoinsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

data class SelectCoinState(
    val query: String? = null,
    val loadedQuery: String? = null,
    val request: Async<List<Coin>> = Uninitialized,
    val loadAll: Boolean = false
) : MavericksState


class SelectCoinViewModel(initialState: SelectCoinState) :
    MavericksViewModel<SelectCoinState>(initialState), KoinComponent {

    private val getCoinsUseCase: GetCoinsUseCase by inject()

    private var searchJob: Job? = null

    private var searchDebounceJob: Job? = null

    private val loadLimit = 100

    init {
        onEach(SelectCoinState::query) { query ->
            searchJob?.cancel()
            searchJob = suspend {
                getCoinsUseCase(query, limit = loadLimit)
            }.execute(retainValue = SelectCoinState::request) {
                val loadAllData = if (it is Success) {
                    it.invoke().size < loadLimit
                } else false
                copy(
                    request = it,
                    loadAll = loadAllData,
                    loadedQuery = if (it is Success) query else loadedQuery
                )
            }
        }
    }

    fun search(q: String) {
        searchDebounceJob?.cancel()
        val query = q.trim()
        if (query.isEmpty()) {
            setState { copy(query = null, loadAll = false) }
        } else {
            searchDebounceJob = viewModelScope.launch {
                delay(500)
                setState { copy(query = q, loadAll = false) }
            }
        }
    }

    fun loadNext() {
        withState { state ->
            if (state.request is Success && !state.loadAll) {
                val offset = state.request.invoke().size
                searchJob = suspend {
                    getCoinsUseCase(state.query, limit = loadLimit, offset = offset)
                }.execute(dispatcher = Dispatchers.IO, retainValue = SelectCoinState::request) {
                    if (it is Success) {
                        val current = request.invoke() ?: listOf()
                        val next = it.invoke()
                        copy(request = Success(current.plus(next)), loadAll = next.size < loadLimit)
                    } else copy(request = it)
                }
            }
        }
    }

}