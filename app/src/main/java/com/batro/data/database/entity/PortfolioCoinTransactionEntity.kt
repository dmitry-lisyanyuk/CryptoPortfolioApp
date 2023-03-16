package com.batro.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal


@Entity(tableName = "portfolio_coin_transaction")
data class PortfolioCoinTransactionEntity(
    val coinId: String,
    val amount: BigDecimal,
    val price: BigDecimal,
    val note: String?,
    @PrimaryKey(autoGenerate = true)
    val coinInfoId: Int? = null
)






