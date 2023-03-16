package com.batro.data.database

import androidx.room.*
import com.batro.data.database.entity.LPTokenSupplyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LPTokenSupplyDao {

    @Query("SELECT * FROM lp_token_supply")
    fun getLPTokenSupplyFlow(): Flow<List<LPTokenSupplyEntity>>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLPTokenSupplyList(list: List<LPTokenSupplyEntity>)

}