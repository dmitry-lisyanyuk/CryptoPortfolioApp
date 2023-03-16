package com.batro.data.repository.lpToken.source

import com.batro.data.api.CryptoApi
import com.batro.data.model.LPToken

class RemoteLPTokenDataSourceImpl(
    private val cryptoApi: CryptoApi
) : RemoteLPTokenDataSource {

    override suspend fun getLPTokens(platformId: String): List<LPToken> {
        return cryptoApi.getLiquidityPools(platformId)
    }
}