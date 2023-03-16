package com.batro.data.mapper

import com.batro.data.database.entity.CoinEntity
import com.batro.data.model.Coin

fun Coin.toEntity(): CoinEntity {
    return CoinEntity(
        coingeckoId = coingeckoId,
        name = name,
        symbol = symbol,
        icon = icon,
        marketCap = marketCap
    )
}

fun CoinEntity.toCoin(): Coin {
    return Coin(
        coingeckoId = coingeckoId,
        name = name,
        symbol = symbol,
        icon = icon,
        marketCap = marketCap
    )
}