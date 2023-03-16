package com.batro.domain.usecase

import com.batro.data.repository.coin.source.LocalCoinDataSource
import com.batro.data.mapper.toCoin
import com.batro.data.model.Coin
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetCoinUseCase(
    private val localCoinDataSource: LocalCoinDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(id: String): Coin {
        return withContext(ioDispatcher) {
            localCoinDataSource.getCoin(id).toCoin()
        }
    }
}