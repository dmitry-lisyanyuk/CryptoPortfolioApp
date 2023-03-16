package com.batro.data.repository.coin.source

import com.batro.data.database.entity.CoinEntity
import com.batro.data.model.Coin

/**
 * The LocalCoinDataSource interface defines methods for storing and retrieving coin data from a local data source.
 */
interface LocalCoinDataSource {

    /**
     * Retrieves a list of coins from the local data source based on the provided search criteria.
     *
     * @param query The search query. If null, all coins will be returned.
     * @param limit The maximum number of coins to retrieve.
     * @param offset The starting index of the coins to retrieve.
     *
     * @return A list of CoinEntity objects that match the search criteria.
     */
    suspend fun getCoinList(query: String?, limit: Int, offset: Int): List<CoinEntity>

    /**
     * Retrieves a list of CoinEntity objects from the local data source based on the provided list of coin IDs.
     *
     * @param ids The list of coin IDs to retrieve.
     *
     * @return A list of CoinEntity objects that match the provided list of coin IDs.
     */
    suspend fun getCoinList(ids: List<String>): List<CoinEntity>

    /**
     * Retrieves a coin from the local data source by ID.
     *
     * @param id The ID of the coin to retrieve.
     *
     * @return The CoinEntity object with the specified ID.
     */
    suspend fun getCoin(id: String): CoinEntity

    /**
     * Saves a list of coins to the local data source.
     *
     * @param coins The list of Coin objects to save.
     */
    suspend fun saveCoinList(coins: List<Coin>)

    /**
     * Save a coin to the local data source.
     *
     * @param coin The Coin objects to save.
     */
    suspend fun saveCoin(coin: Coin)
}

