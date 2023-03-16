package com.batro.domain.usecase

import com.batro.data.repository.coin.CoinRepository
import com.batro.data.model.Coin
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetCoinsUseCase(
    private val coinRepository: CoinRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(query: String?, limit: Int = 50, offset: Int = 0): List<Coin> {
        return withContext(ioDispatcher) {
            coinRepository.getCoinList(query, limit, offset)
        }
    }
}