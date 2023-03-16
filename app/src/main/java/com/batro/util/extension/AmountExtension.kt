package com.batro.util.extension

import org.web3j.utils.Convert
import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode

fun Long.longAmountToString(): String {
    return try {
        toBigDecimal().divide(BigDecimal(100)).setScale(2, RoundingMode.DOWN).toPlainString()
    } catch (e: Exception) {
        ""
    }
}

fun Double.formatDecimal(): String {
    return if (this == this.toLong().toDouble())
        String.format("%d", this.toLong())
    else
        String.format("%s", this)
}

fun BigInteger.fromWei(unit: Convert.Unit = Convert.Unit.ETHER, maxScale: Int = 6): String {
    val number = Convert.fromWei(toBigDecimal(), unit)
    val scale = maxScale - number.precision() + number.scale()
    return number.setScale(scale, RoundingMode.DOWN).stripTrailingZeros().toPlainString()
}

//val scale = if (this == BigInteger.ZERO) {
//    0
//} else {
//    kotlin.math.max(4, kotlin.math.min(maxScale, 20 - toString().length))
//}
//return Convert.fromWei(toBigDecimal(), unit).setScale(scale, RoundingMode.DOWN).toPlainString()


fun convertUnitOfDecimal(decimals: Int): Convert.Unit {
    return when (decimals) {
        0 -> Convert.Unit.WEI
        3 -> Convert.Unit.KWEI
        6 -> Convert.Unit.MWEI
        9 -> Convert.Unit.GWEI
        12 -> Convert.Unit.SZABO
        15 -> Convert.Unit.FINNEY
        18 -> Convert.Unit.ETHER
        21 -> Convert.Unit.KETHER
        24 -> Convert.Unit.METHER
        27 -> Convert.Unit.GETHER
        else -> Convert.Unit.ETHER
    }
}