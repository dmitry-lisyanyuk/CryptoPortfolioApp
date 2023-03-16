package com.batro.data.api.model

import androidx.annotation.Keep


@Keep
data class CoinMarketChartResponse(
    val prices: List<List<Number>>
)
