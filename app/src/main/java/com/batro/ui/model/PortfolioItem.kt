package com.batro.ui.model


import androidx.compose.runtime.Stable
import com.batro.data.model.Coin
import com.batro.data.model.LPToken

import java.io.Serializable
import java.math.BigDecimal
import java.math.BigInteger


sealed interface PortfolioItem {
    val id: Int
}

@Stable
data class PortfolioCoinItem(
    val currency: Coin,
    val amount: BigInteger,
    val totalPriceUSD: Long,
    val initialPriceUSD: Long,
    val price: BigDecimal,
    val note: String?,
    override val id: Int
) : PortfolioItem, Serializable


data class PortfolioLPItem(
    val lpToken: LPToken,
    val lpAmount: BigInteger,
    val token1Amount: BigInteger,
    val token2Amount: BigInteger,
    val lpPriceUSD: Long,
    val initialPriceUSD: Long,
    val lpProfitUSD: Long,
    override val id: Int
) : PortfolioItem, Serializable
