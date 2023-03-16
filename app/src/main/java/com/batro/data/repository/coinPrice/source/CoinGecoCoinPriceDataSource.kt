package com.batro.data.repository.coinPrice.source

import com.batro.data.Constants
import com.batro.data.api.CoingecoApi
import org.json.JSONObject

import java.math.BigDecimal

class CoinGecoCoinPriceDataSource(
    private val coingecoApi: CoingecoApi
) : CoinPriceDataSource {

    override suspend fun requestCoinPrices(set: Set<String>): Map<String, BigDecimal> {
        val jsonResponse = coingecoApi.gePrices(
            set.joinToString(separator = ","),
            Constants.DEFAULT_VS_CURRENCIES
        ).toString().let(::JSONObject)
        val map = mutableMapOf<String, BigDecimal>()
        set.forEach {
            map[it] = jsonResponse.getJSONObject(it).getDouble(Constants.DEFAULT_VS_CURRENCIES)
                .toBigDecimal()
        }
        return map
    }
}