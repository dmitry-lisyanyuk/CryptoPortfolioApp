package com.batro.data.database

import androidx.room.*
import com.batro.data.database.entity.PortfolioCoinTransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PortfolioCoinTransactionDao {

    @Query("SELECT * FROM portfolio_coin_transaction")
    fun getList(): Flow<List<PortfolioCoinTransactionEntity>>

    @Query("SELECT * FROM portfolio_coin_transaction WHERE coinId = :coinId")
    fun getListByCoinId(coinId: String): Flow<List<PortfolioCoinTransactionEntity>>

    @Query("DELETE FROM portfolio_coin_transaction WHERE coinId = :coinId")
    suspend fun deleteByCoinId(coinId: String)

    @Query("DELETE FROM portfolio_coin_transaction WHERE coinInfoId = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT * FROM portfolio_coin_transaction WHERE coinInfoId = :id")
    suspend fun getTransactionById(id: Int): PortfolioCoinTransactionEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(portfolioCoinTransactionEntity: PortfolioCoinTransactionEntity)

    @Update
    suspend fun updateTransaction(portfolioCoinTransactionEntity: PortfolioCoinTransactionEntity)

}