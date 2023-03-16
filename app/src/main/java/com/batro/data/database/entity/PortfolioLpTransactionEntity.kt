package com.batro.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.batro.data.model.PortfolioLpTransaction
import org.web3j.abi.datatypes.Address
import java.math.BigDecimal

/**
 * This class represents an entity that holds information about a portfolio transaction involving an LP token.
 * It is used to store data in a database table named "portfolio_lp_transaction".
 * @property lpTokenAddress The address of the LP token involved in the transaction.
 * @property lpAmount The amount of the LP token involved in the transaction.
 * @property address The address of the transaction, if applicable.
 * @property amount1 The amount of the first token in the LP pair involved in the transaction.
 * @property amount2 The amount of the second token in the LP pair involved in the transaction.
 * @property price1 The price of the first token in the LP pair at the time of the transaction, if applicable.
 * @property price2 The price of the second token in the LP pair at the time of the transaction, if applicable.
 * @property coinInfoId The ID of transaction.
 * @see [PortfolioLpTransaction]
 */

@Entity(tableName = "portfolio_lp_transaction")
data class PortfolioLpTransactionEntity(
    val lpTokenAddress: String,
    val lpAmount: BigDecimal,
    val address: Address?,
    val amount1: BigDecimal,
    val amount2: BigDecimal,
    val price1: BigDecimal?,
    val price2: BigDecimal?,
    val note: String?,
    @PrimaryKey(autoGenerate = true)
    val coinInfoId: Int? = null
)