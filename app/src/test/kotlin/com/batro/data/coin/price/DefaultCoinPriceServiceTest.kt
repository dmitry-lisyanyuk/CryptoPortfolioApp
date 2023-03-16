@file:OptIn(ExperimentalCoroutinesApi::class)

package com.batro.data.coin.price

import com.batro.data.repository.coinPrice.source.CoinPriceDataSource
import com.batro.data.repository.coinPrice.DefaultCoinPriceService
import io.mockk.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.*
import org.junit.*
import java.math.BigDecimal

class DefaultCoinPriceServiceTest {

    @Test
    fun testCoinPriceChanges_singleCoin() = runTest {
        val dataSource = mockk<CoinPriceDataSource>()
        val priceMap = mapOf("BTC" to BigDecimal("50000"))
        coEvery { dataSource.requestCoinPrices(setOf("BTC")) } returns priceMap
        val coinPriceService = DefaultCoinPriceService(
            this,
            dataSource
        )
        val flow = coinPriceService.coinPriceChanges("BTC")
        val expected = priceMap["BTC"]!!
        val actual = flow.take(1).toList()[0]
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun testCoinPriceChanges_multipleCoins() = runTest {
        val dataSource = mockk<CoinPriceDataSource>()
        val priceMap = mapOf(
            "BTC" to BigDecimal("50000"),
            "ETH" to BigDecimal("3000")
        )
        coEvery { dataSource.requestCoinPrices(setOf("BTC", "ETH")) } returns priceMap
        val coinPriceService = DefaultCoinPriceService(
            this,
            dataSource
        )
        val flow = coinPriceService.coinPriceChanges(setOf("BTC", "ETH"))
        val expected = priceMap
        val actual = flow.take(1).toList()[0]
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun testCoinPriceChanges_update() = runTest {
        val dataSource = mockk<CoinPriceDataSource>()
        val firstPriceMap = mapOf("BTC" to BigDecimal("50000"))
        val secondPriceMap = mapOf("BTC" to BigDecimal("55000"))
        coEvery { dataSource.requestCoinPrices(setOf("BTC")) } returnsMany listOf(
            firstPriceMap,
            secondPriceMap
        )
        val coinPriceService = DefaultCoinPriceService(
            this,
            dataSource
        )
        val flow = coinPriceService.coinPriceChanges("BTC")
        val expected = listOf(
            firstPriceMap["BTC"]!!,
            secondPriceMap["BTC"]!!
        )
        val actual = flow.take(expected.size).toList()
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun testCoinPriceChanges_update_distinctUntilChanged() = runTest {
        val dataSource = mockk<CoinPriceDataSource>()
        val firstPriceMap = mapOf("BTC" to BigDecimal("50000"))
        val secondPriceMap = mapOf("BTC" to BigDecimal("50000"))
        val thirdPriceMap = mapOf("BTC" to BigDecimal("55000"))
        coEvery { dataSource.requestCoinPrices(setOf("BTC")) } returnsMany listOf(
            firstPriceMap,
            secondPriceMap,
            thirdPriceMap
        )
        val coinPriceService = DefaultCoinPriceService(
            this,
            dataSource
        )
        val flow = coinPriceService.coinPriceChanges("BTC")
        val actual = flow.take(2).toList().last()
        Assert.assertEquals(BigDecimal("55000"), actual)
    }
}