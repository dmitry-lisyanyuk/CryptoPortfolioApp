package com.batro.data.repository.coinPrice.source

import java.math.BigDecimal


/**
 * Interface that provides access to coin price data.
 */
interface CoinPriceDataSource {

    /**
     * Requests the current prices for the specified set of coin IDs.
     *
     * @param set the set of coin IDs to request prices for.
     * @return a map of coin IDs to their corresponding prices.
     */
    suspend fun requestCoinPrices(set: Set<String>): Map<String, BigDecimal>
}