package com.batro.data.database

import androidx.room.*
import com.batro.data.database.entity.*

@Dao
interface LPTokenDao {

    @Query("SELECT * FROM lp_token WHERE contractAddress IN (:ids)")
    suspend fun getLPTokenListByContractAddressSet(ids:Set<String>): List<LPTokenEntity>

    @Query("SELECT * FROM lp_token WHERE chainId = :platformId")
    suspend fun getLPTokenListByPlatformId(platformId:String): List<LPTokenEntity>

    @Query("SELECT * FROM lp_token WHERE contractAddress = :address")
    suspend fun getLPByAddress(address:String): LPTokenEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLpToken(lpTokenEntity: LPTokenEntity)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLpTokenList(lpTokenEntityList: List<LPTokenEntity>)

}