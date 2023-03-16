package com.batro.domain.usecase

import com.batro.data.database.entity.LPTokenSupplyEntity
import com.batro.data.model.LPToken
import com.batro.data.model.PortfolioLpTransaction
import com.batro.ui.model.PortfolioLPItem
import com.batro.util.extension.convertUnitOfDecimal
import org.web3j.utils.Convert
import java.math.BigDecimal
import java.math.MathContext

class CalcPortfolioLPItemUseCase {

    operator fun invoke(
        lpToken: LPToken,
        supply: LPTokenSupplyEntity,
        txList: List<PortfolioLpTransaction>,
        prices: Map<String, BigDecimal>,
        id: Int
    ): PortfolioLPItem {

        val userAmount =
            Convert.toWei(txList.sumOf { it.lpAmount }, convertUnitOfDecimal(lpToken.lpDecimals))
                .toBigInteger()
        val percent = userAmount.toBigDecimal()
            .divide(supply.totalSupply.toBigDecimal(), MathContext.DECIMAL128)

        val amount0 = txList.sumOf { it.amount1 }
        val amount1 = txList.sumOf { it.amount2 }
        val c0 = supply.currency0Amount.toBigDecimal().multiply(percent)
        val c1 = supply.currency1Amount.toBigDecimal().multiply(percent)

        val am0 = Convert.fromWei(c0, convertUnitOfDecimal(lpToken.currency0Decimals))
        val am1 = Convert.fromWei(c1, convertUnitOfDecimal(lpToken.currency1Decimals))

        val currentPrice0 = prices[lpToken.coin0.coingeckoId]
        val currentPrice1 = prices[lpToken.coin1.coingeckoId]

        val initialPrice0 = currentPrice0
        val initialPrice1 = currentPrice1

        val a0 = am0.minus(amount0).multiply(currentPrice0)
        val a1 = am1.minus(amount1).multiply(currentPrice1)

        val profitUSDTLong = (a0.plus(a1).toDouble() * 100).toLong()

        val lpPriceUSDTLong =
            (am0.multiply(currentPrice0).add(am1.multiply(currentPrice1)).toDouble() * 100).toLong()

        val initialPriceUSDTLong =
            (amount0.multiply(initialPrice0).add(amount1.multiply(initialPrice1))
                .toDouble() * 100).toLong()

        return PortfolioLPItem(
            lpToken,
            userAmount,
            c0.toBigInteger(),
            c1.toBigInteger(),
            lpPriceUSDTLong,
            initialPriceUSDTLong,
            profitUSDTLong,
            id
        )
    }
}

/*
fun calcPortfolioLPItem(
    info: PortfolioLpTransaction,
    userAmount: BigInteger,
    totalSupply: BigInteger,
    reserve0: BigInteger,
    reserve1: BigInteger,
    prices:Map<String, BigDecimal>
): PortfolioLPItem {
    val percent = userAmount.toBigDecimal().divide(totalSupply.toBigDecimal(), MathContext.DECIMAL128)

    val c0 = reserve0.toBigDecimal().multiply(percent)
    val c1 = reserve1.toBigDecimal().multiply(percent)

    val am0 = Convert.fromWei(c0, convertUnitOfDecimal(info.lpToken.currency0Decimals))
    val am1 = Convert.fromWei(c1, convertUnitOfDecimal(info.lpToken.currency1Decimals))

    val currentPrice0 = prices[info.lpToken.coin0.coingeckoId]
    val currentPrice1 = prices[info.lpToken.coin1.coingeckoId]

    val initialPrice0 = info.price1 ?: currentPrice0
    val initialPrice1 = info.price2 ?: currentPrice1

    val a0 = am0.minus(info.amount1).multiply(currentPrice0)
    val a1 = am1.minus(info.amount2).multiply(currentPrice1)

    val profitUSDTLong = (a0.plus(a1).toDouble() * 100).toLong()

    val lpPriceUSDTLong = (am0.multiply(currentPrice0).add(am1.multiply(currentPrice1)).toDouble() * 100).toLong()

    val initialPriceUSDTLong = (info.amount1.multiply(initialPrice0).add(info.amount2.multiply(initialPrice1)).toDouble() * 100).toLong()

    return PortfolioLPItem(
        info.lpToken,
        userAmount,
        c0.toBigInteger(),
        c1.toBigInteger(),
        lpPriceUSDTLong,
        initialPriceUSDTLong,
        profitUSDTLong,
        info.coinInfoId ?: 0
    )
}*/
