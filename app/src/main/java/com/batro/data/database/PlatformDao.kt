package com.batro.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.batro.data.database.entity.PlatformEntity

@Dao
interface PlatformDao {

    @Query("SELECT * FROM platform")
    suspend fun getPlatformList(): List<PlatformEntity>

    @Query("SELECT * FROM platform WHERE  id = :id")
    suspend fun getPlatform(id: String): PlatformEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlatformList(platforms: List<PlatformEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlatform(platform: PlatformEntity)

}