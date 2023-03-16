package com.batro.data.api

import com.batro.data.model.Coin
import com.batro.data.model.LPToken
import com.batro.data.model.Platform
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoApi {

    @GET("currencies")
    suspend fun getCoinList(
        @Query("q") q: String? = null,
        @Query("limit") limit: Int = 50,
        @Query("offset") offset: Int = 0,
    ): List<Coin>

    @GET("liquidityPools")
    suspend fun getLiquidityPools(@Query("platformId") platformId: String): List<LPToken>

    @GET("assetPlatforms")
    suspend fun getAssetPlatforms(): List<Platform>

}