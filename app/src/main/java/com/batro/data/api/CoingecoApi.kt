package com.batro.data.api

import com.batro.data.Constants
import com.batro.data.api.model.CoinMarketChartResponse
import com.google.gson.JsonObject
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoingecoApi {

    @GET("simple/price")
    suspend fun gePrices(
        @Query("ids") ids: String,
        @Query("vs_currencies") vs_currencies: String = Constants.DEFAULT_VS_CURRENCIES,
    ): JsonObject

    @GET("coins/{id}/market_chart")
    suspend fun marketChart(
        @Path("id") id: String,
        @Query("days") days: String = "30",
        @Query("vs_currency") vs_currency: String = Constants.DEFAULT_VS_CURRENCIES,
    ): CoinMarketChartResponse
}