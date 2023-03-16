package com.batro.data.model

import com.batro.data.database.entity.PortfolioLpTransactionEntity
import org.web3j.abi.datatypes.Address
import java.math.BigDecimal

/**
 * Represents a portfolio transaction involving an LP token.
 * @param lpToken The [LPToken] involved in the transaction.
 * @param lpAmount The amount of the LP token involved in the transaction.
 * @param address The address of the transaction, if applicable.
 * @param amount1 The amount of the first token in the LP pair involved in the transaction.
 * @param amount2 The amount of the second token in the LP pair involved in the transaction.
 * @param price1 The price of the first token in the LP pair at the time of the transaction, if applicable.
 * @param price2 The price of the second token in the LP pair at the time of the transaction, if applicable.
 * @param note The note of the transaction.
 * @param coinInfoId The ID of the associated [PortfolioLpTransactionEntity] in the database.
 * @see [PortfolioLpTransactionEntity]
 */

data class PortfolioLpTransaction(
    val lpToken: LPToken,
    val lpAmount: BigDecimal,
    val address: Address?,
    val amount1: BigDecimal,
    val amount2: BigDecimal,
    val price1: BigDecimal?,
    val price2: BigDecimal?,
    val note:String?,
    val coinInfoId: Int? = null
) : PortfolioTransaction