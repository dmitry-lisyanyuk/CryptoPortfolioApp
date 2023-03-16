package com.batro.data.repository.coinPrice

import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

/**
 * Interface that provides access to live coin price data.
 */
interface CoinPriceService {

    /**
     * Subscribes to updates of the price of a specific coin.
     *
     * This method returns a [Flow] that emits the latest price of the specified coin and any subsequent updates to the price.
     * The [Flow] will only emit updates for the specified coin ID.
     *
     * @param id the ID of the coin to track the price of.
     * @return a [Flow] that emits the latest price and any subsequent updates of the specified coin.
     */
    fun coinPriceChanges(id: String): Flow<BigDecimal>


    /**
     * Subscribes to updates of the prices of a set of coins.
     *
     * This method returns a [Flow] that emits the latest prices of the specified coins and any subsequent updates to the prices.
     * The [Flow] will only emit updates for the specified coin IDs.
     *
     * @param ids the set of coin IDs to track the prices of.
     * @return a [Flow] that emits the latest prices and any subsequent updates of the specified coins.
     */
    fun coinPriceChanges(ids: Set<String>): Flow<Map<String, BigDecimal>>

}