package com.batro.data.repository.coin.source

import com.batro.data.api.CryptoApi
import com.batro.data.model.Coin

class RemoteCoinDataSourceImpl(private val cryptoApi: CryptoApi) : RemoteCoinDataSource {

    override suspend fun getCoinList(
        query: String?,
        limit: Int,
        offset: Int
    ): List<Coin> {
        return cryptoApi.getCoinList(query, limit, offset)
    }

}