package com.batro.data.repository.lpToken.source

import androidx.room.withTransaction
import com.batro.data.repository.coin.source.LocalCoinDataSource
import com.batro.data.database.AppDatabase
import com.batro.data.database.LPTokenDao
import com.batro.data.database.entity.LPTokenEntity
import com.batro.data.mapper.toCoin
import com.batro.data.mapper.toEntity
import com.batro.data.mapper.toLPToken
import com.batro.data.model.Coin
import com.batro.data.model.LPToken

class LocalLPTokenDataSourceImpl(
    private val database: AppDatabase,
    private val lpTokenDao: LPTokenDao,
    private val localCoinDataSource: LocalCoinDataSource
) : LocalLPTokenDataSource {

    override suspend fun getLPTokens(platformId: String): List<LPToken> {
        val lpEntityList = lpTokenDao.getLPTokenListByPlatformId(platformId)
        return getLpTokenListByEntityList(lpEntityList)
    }

    override suspend fun getLPTokens(contractAddressSet: Set<String>): List<LPToken> {
        val lpEntityList = lpTokenDao.getLPTokenListByContractAddressSet(contractAddressSet)
        return getLpTokenListByEntityList(lpEntityList)
    }

    override suspend fun getLPTokensEntity(contractAddressSet: Set<String>): List<LPTokenEntity> {
        return lpTokenDao.getLPTokenListByContractAddressSet(contractAddressSet)
    }

    override suspend fun saveLPToken(lpToken: LPToken) {
        localCoinDataSource.saveCoin(lpToken.coin0)
        localCoinDataSource.saveCoin(lpToken.coin1)
        lpTokenDao.insertLpToken(lpToken.toEntity())
    }

    override suspend fun saveLPTokenList(lpTokens: List<LPToken>) {
        database.withTransaction {
            val coinList = mutableListOf<Coin>()
            lpTokens.forEach {
                coinList.add(it.coin0)
                coinList.add(it.coin1)
            }
            localCoinDataSource.saveCoinList(coinList.distinctBy { it.coingeckoId })
            lpTokenDao.insertLpTokenList(lpTokens.map { it.toEntity() })
        }
    }

    override suspend fun getLPTokenByAddress(address: String): LPTokenEntity {
        return lpTokenDao.getLPByAddress(address)
    }

    private suspend fun getLpTokenListByEntityList(lpEntityList: List<LPTokenEntity>): List<LPToken> {
        val coinIds = mutableSetOf<String>()
        lpEntityList.forEach {
            coinIds.add(it.currency0Id)
            coinIds.add(it.currency1Id)
        }
        val coins = localCoinDataSource.getCoinList(coinIds.toList())
        return lpEntityList.map { lp ->
            val coin0 = coins.first { it.coingeckoId == lp.currency0Id }.toCoin()
            val coin1 = coins.first { it.coingeckoId == lp.currency1Id }.toCoin()
            lp.toLPToken(coin0, coin1)
        }
    }
}