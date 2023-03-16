package com.batro.data.repository.coin

import com.batro.data.mapper.toCoin
import com.batro.data.model.Coin
import com.batro.data.repository.coin.source.LocalCoinDataSource
import com.batro.data.repository.coin.source.RemoteCoinDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class CoinRepositoryImpl(
    private val coroutineContext: CoroutineScope,
    private val remoteDataSource: RemoteCoinDataSource,
    private val localDataSource: LocalCoinDataSource
) : CoinRepository {

    override suspend fun getCoinList(query: String?, limit: Int, offset: Int): List<Coin> {
        return if (offset == 0) {
            try {
                remoteDataSource.getCoinList(query, limit, offset).apply {
                    coroutineContext.launch {
                        localDataSource.saveCoinList(this@apply)
                    }
                }
            } catch (e: Exception) {
                localDataSource.getCoinList(query, limit, offset).map { it.toCoin() }
            }
        } else {
            remoteDataSource.getCoinList(query, limit, offset)
        }
    }
}