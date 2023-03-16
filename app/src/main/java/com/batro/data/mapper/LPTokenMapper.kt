package com.batro.data.mapper

import com.batro.data.database.entity.LPTokenEntity
import com.batro.data.model.Coin
import com.batro.data.model.LPToken

fun LPTokenEntity.toLPToken(coin0: Coin, coin1: Coin): LPToken {
    return LPToken(
        contractAddress = contractAddress,
        pid = pid,
        lpDecimals = lpDecimals,
        currency0Decimals = currency0Decimals,
        currency1Decimals = currency1Decimals,
        serviceId = serviceId,
        chainId = chainId,
        poolExtra = poolExtra,
        coin0 = coin0,
        coin1 = coin1
    )
}

fun LPToken.toEntity(): LPTokenEntity {
    return LPTokenEntity(
        contractAddress = contractAddress,
        pid = pid,
        lpDecimals = lpDecimals,
        currency0Decimals = currency0Decimals,
        currency1Decimals = currency1Decimals,
        currency0Id = coin0.coingeckoId,
        currency1Id = coin1.coingeckoId,
        serviceId = serviceId,
        chainId = chainId,
        poolExtra = poolExtra
    )
}