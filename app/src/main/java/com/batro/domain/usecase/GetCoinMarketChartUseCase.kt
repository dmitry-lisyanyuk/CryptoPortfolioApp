package com.batro.domain.usecase

import com.batro.data.api.CoingecoApi
import com.batro.ui.model.PriceSnapshot
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetCoinMarketChartUseCase(
    private val api: CoingecoApi,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(id: String): List<PriceSnapshot> {
        return withContext(ioDispatcher) {
            api.marketChart(id).prices
                .map {
                    PriceSnapshot(
                        it.first().toLong(),
                        it.last().toFloat()
                    )
                }
        }
    }
}