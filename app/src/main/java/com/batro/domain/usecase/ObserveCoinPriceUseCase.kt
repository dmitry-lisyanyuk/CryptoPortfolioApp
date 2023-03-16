package com.batro.domain.usecase

import com.batro.data.repository.coinPrice.CoinPriceService
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

class ObserveCoinPriceUseCase(
    private val currencyPriceService: CoinPriceService
) {
    operator fun invoke(id: String): Flow<BigDecimal> {
        return currencyPriceService.coinPriceChanges(id)
    }
}