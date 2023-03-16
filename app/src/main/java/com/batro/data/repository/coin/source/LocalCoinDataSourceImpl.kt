package com.batro.data.repository.coin.source

import com.batro.data.database.CoinDao
import com.batro.data.database.entity.CoinEntity
import com.batro.data.mapper.toEntity
import com.batro.data.model.Coin

class LocalCoinDataSourceImpl(private val coinDao: CoinDao) : LocalCoinDataSource {

    override suspend fun getCoinList(query: String?, limit: Int, offset: Int): List<CoinEntity> {
        return coinDao.getCoinList()
    }

    override suspend fun getCoinList(ids: List<String>): List<CoinEntity> {
        return coinDao.getCoinList(ids)
    }

    override suspend fun getCoin(id: String): CoinEntity {
        return coinDao.getCoin(id)
    }

    override suspend fun saveCoinList(coins: List<Coin>) {
        val coinEntities = coins.map { it.toEntity() }
        coinDao.insertCoinList(coinEntities)
    }

    override suspend fun saveCoin(coin: Coin) {
        coinDao.insertCoin(coin.toEntity())
    }
}