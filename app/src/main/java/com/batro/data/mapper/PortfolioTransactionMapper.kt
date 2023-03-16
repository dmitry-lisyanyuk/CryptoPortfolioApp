package com.batro.data.mapper

import com.batro.data.database.entity.PortfolioCoinTransactionEntity
import com.batro.data.database.entity.PortfolioLpTransactionEntity
import com.batro.data.model.Coin
import com.batro.data.model.LPToken
import com.batro.data.model.PortfolioCoinTransaction
import com.batro.data.model.PortfolioLpTransaction

fun PortfolioCoinTransactionEntity.toPortfolioCoinTransaction(coin: Coin): PortfolioCoinTransaction {
    return PortfolioCoinTransaction(
        coin = coin,
        amount = amount,
        price = price,
        note = note,
        coinInfoId = coinInfoId
    )
}

fun PortfolioCoinTransaction.toEntity(): PortfolioCoinTransactionEntity {
    return PortfolioCoinTransactionEntity(
        coinId = coin.coingeckoId,
        amount = amount,
        price = price,
        note = note,
        coinInfoId = coinInfoId
    )
}

fun PortfolioLpTransactionEntity.toPortfolioLpTransaction(lpToken: LPToken): PortfolioLpTransaction {
    return PortfolioLpTransaction(
        lpToken = lpToken,
        lpAmount = lpAmount,
        address = address,
        amount1 = amount1,
        amount2 = amount2,
        price1 = price1,
        price2 = price2,
        note = note,
        coinInfoId = coinInfoId
    )
}

fun PortfolioLpTransaction.toEntity(): PortfolioLpTransactionEntity {
    return PortfolioLpTransactionEntity(
        lpTokenAddress = lpToken.contractAddress,
        lpAmount = lpAmount,
        address = address,
        amount1 = amount1,
        amount2 = amount2,
        price1 = price1,
        price2 = price2,
        note = note,
        coinInfoId = coinInfoId
    )
}
