package com.batro.data.repository.coinPrice

import com.batro.data.repository.coinPrice.source.CoinPriceDataSource
import com.batro.util.extension.interval
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.math.BigDecimal

class DefaultCoinPriceService(
    private val coroutineContext: CoroutineScope,
    private val dataSource: CoinPriceDataSource
) : CoinPriceService {

    private val coinRequestsMutex = Mutex()

    private val coinRequests = mutableListOf<CoinPriceRequest>()

    private val priceStateFlow = MutableStateFlow<Map<String, BigDecimal>>(mapOf())

    private var requestJob: Job? = null

    private fun requestUpdate() {
        requestJob?.cancel()
        requestJob = interval(15_000, 200).onEach {
            val coinIds = coinRequestsMutex.withLock {
                coinRequests.map { it.ids }.flatten().toSet()
            }
            if (coinIds.isEmpty()) {
                priceStateFlow.value = mapOf()
                requestJob?.cancel()
            } else {
                priceStateFlow.value = dataSource.requestCoinPrices(coinIds)
            }
        }.retryWhen { _, _ ->
            delay(5_000)
            true
        }.launchIn(coroutineContext)
    }


    override fun coinPriceChanges(id: String): Flow<BigDecimal> {
        val request = CoinPriceRequest(setOf(id))
        return priceStateFlow.onStart {
            coinRequestsMutex.withLock {
                if (!coinRequests.contains(request)) {
                    coinRequests.add(request)
                }
            }
        }.mapNotNull {
            if (it.keys.contains(id)) {
                it[id]
            } else {
                requestUpdate()
                null
            }
        }.onCompletion {
            delay(1000)
            cancelRequest(request)
        }.distinctUntilChanged()
    }

    override fun coinPriceChanges(ids: Set<String>): Flow<Map<String, BigDecimal>> {
        val request = CoinPriceRequest(ids)
        return priceStateFlow.onStart {
            coinRequestsMutex.withLock {
                if (!coinRequests.contains(request)) {
                    coinRequests.add(request)
                }
            }
        }.mapNotNull {
            if (it.keys.containsAll(ids)) {
                it
            } else {
                requestUpdate()
                null
            }
        }.onCompletion {
            delay(1000)
            cancelRequest(request)
        }.distinctUntilChanged()
    }

    private suspend fun cancelRequest(request: CoinPriceRequest) {
        coinRequestsMutex.withLock {
            coinRequests.remove(request)
            if (coinRequests.isEmpty()) {
                requestJob?.cancel()
            }
        }
    }

    private data class CoinPriceRequest(
        val ids: Set<String>
    )
}