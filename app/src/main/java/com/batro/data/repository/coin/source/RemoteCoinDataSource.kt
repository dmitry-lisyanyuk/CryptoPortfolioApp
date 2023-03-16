package com.batro.data.repository.coin.source

import com.batro.data.model.Coin

/**
 * The RemoteCoinDataSource interface defines methods for fetching coin data from a remote data source.
 */
interface RemoteCoinDataSource {

    /**
     * Fetches a list of coins from the remote data source.
     *
     * @param query The search query. If null, all coins will be returned.
     * @param limit The maximum number of coins to retrieve.
     * @param offset The starting index of the coins to retrieve.
     *
     * @return A list of Coin objects that match the search criteria.
     */
    suspend fun getCoinList(query: String?, limit: Int, offset: Int): List<Coin>
}

