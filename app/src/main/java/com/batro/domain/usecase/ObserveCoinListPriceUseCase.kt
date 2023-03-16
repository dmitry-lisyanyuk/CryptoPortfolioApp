package com.batro.domain.usecase

import com.batro.data.repository.coinPrice.CoinPriceService
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

class ObserveCoinListPriceUseCase(
    private val coinPriceService: CoinPriceService
) {
    operator fun invoke(ids: Set<String>): Flow<Map<String, BigDecimal>> {
        return coinPriceService.coinPriceChanges(ids)
    }
}