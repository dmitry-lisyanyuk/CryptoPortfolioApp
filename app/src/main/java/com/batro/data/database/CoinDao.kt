package com.batro.data.database

import androidx.room.*
import com.batro.data.database.entity.CoinEntity

@Dao
interface CoinDao {

    @Query("SELECT * FROM coin ORDER BY marketCap DESC")
    suspend fun getCoinList(): List<CoinEntity>

    @Query("SELECT * FROM coin WHERE coingeckoId IN (:ids)")
    suspend fun getCoinList(ids: List<String>): List<CoinEntity>

    @Query("SELECT * FROM coin WHERE coingeckoId = :id")
    suspend fun getCoin(id: String): CoinEntity

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoinList(list: List<CoinEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoin(coinEntity: CoinEntity)

}