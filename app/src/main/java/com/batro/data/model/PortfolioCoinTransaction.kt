package com.batro.data.model

import com.batro.data.database.entity.PortfolioCoinTransactionEntity
import java.math.BigDecimal

/**
 * Represents a portfolio transaction involving a single coin.
 * @param coin The [Coin] involved in the transaction.
 * @param amount The amount of the coin involved in the transaction.
 * @param price The price of the coin at the time of the transaction.
 * @param note The note of the transaction.
 * @param coinInfoId The ID of the associated [PortfolioCoinTransactionEntity] in the database.
 * @see [PortfolioCoinTransactionEntity]
 */
data class PortfolioCoinTransaction(
    val coin: Coin,
    val amount: BigDecimal,
    val price: BigDecimal,
    val note: String?,
    val coinInfoId: Int? = null
) : PortfolioTransaction