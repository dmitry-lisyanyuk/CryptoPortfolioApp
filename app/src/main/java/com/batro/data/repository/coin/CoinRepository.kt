package com.batro.data.repository.coin

import com.batro.data.model.Coin

/**
 * The CoinRepository interface provides methods to retrieve a list of coins.
 */
interface CoinRepository {

    /**
     * Retrieves a list of coins based on the provided search criteria.
     *
     * @param query The search query. If null, all coins will be returned.
     * @param limit The maximum number of coins to retrieve.
     * @param offset The starting index of the coins to retrieve.
     *
     * @return A list of Coin objects that match the search criteria.
     */
    suspend fun getCoinList(query: String?, limit: Int, offset: Int): List<Coin>

}

