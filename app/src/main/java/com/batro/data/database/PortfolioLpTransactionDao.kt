package com.batro.data.database

import androidx.room.*
import com.batro.data.database.entity.*

import kotlinx.coroutines.flow.Flow

@Dao
interface PortfolioLpTransactionDao {

    @Query("SELECT * FROM portfolio_lp_transaction")
    fun getList(): Flow<List<PortfolioLpTransactionEntity>>

    @Query("SELECT * FROM portfolio_lp_transaction WHERE  lpTokenAddress = :address")
    fun getListByContractAddress(address: String): Flow<List<PortfolioLpTransactionEntity>>

    @Query("SELECT * FROM portfolio_lp_transaction WHERE  coinInfoId = :id")
    suspend fun getLpTransactionById(id: Int): PortfolioLpTransactionEntity

    @Query("DELETE FROM portfolio_lp_transaction WHERE coinInfoId = :id")
    suspend fun deleteById(id: Int)

    @Query("DELETE FROM portfolio_lp_transaction WHERE lpTokenAddress = :contract")
    suspend fun deleteByContract(contract: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(portfolioLpTransactionEntity: PortfolioLpTransactionEntity)

    @Update
    suspend fun updateTransaction(portfolioLpTransactionEntity: PortfolioLpTransactionEntity)

}