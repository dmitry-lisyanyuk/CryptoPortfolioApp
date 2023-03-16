package com.batro.domain.usecase

import com.batro.ui.model.*
import com.batro.util.extension.longAmountToString
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode
import kotlin.math.absoluteValue

class GetPortfolioTotalUseCase {

    operator fun invoke(portfolioItemList: List<PortfolioItem>?): PortfolioTotal? {
        if (portfolioItemList == null || portfolioItemList.isEmpty()) return null
        val totalPriceUSD = portfolioItemList.sumOf {
            when (it) {
                is PortfolioCoinItem -> it.totalPriceUSD
                is PortfolioLPItem -> it.lpPriceUSD - it.initialPriceUSD
            }
        }
        val initialPriceUSD = portfolioItemList.sumOf {
            when (it) {
                is PortfolioCoinItem -> it.initialPriceUSD
                is PortfolioLPItem -> 0
            }
        }
        val profit = totalPriceUSD - initialPriceUSD

        return PortfolioTotal(
            totalPrice = "$${totalPriceUSD.longAmountToString()}",
            profit = "${if (profit < 0) "-" else ""}$${profit.absoluteValue.longAmountToString()}",
            profitType = when {
                profit == 0L -> ProfitType.INDIFFERENT
                profit > 0L -> ProfitType.POSITIVE
                else -> ProfitType.NEGATIVE
            },
            profitPercent = if (initialPriceUSD == 0L) {
                "100%"
            } else {
                profit.toBigDecimal()
                    .divide(initialPriceUSD.toBigDecimal(), MathContext.DECIMAL128)
                    .multiply(BigDecimal(100)).setScale(1, RoundingMode.DOWN)
                    .toPlainString() + "%"
            }
        )
    }
}