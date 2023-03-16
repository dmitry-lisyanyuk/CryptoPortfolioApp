package com.batro.data.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.batro.data.database.converters.BaseConverter
import com.batro.data.database.entity.*

@Database(
    version = 2,
    entities = [
        CoinEntity::class,
        LPTokenEntity::class,
        PortfolioCoinTransactionEntity::class,
        PortfolioLpTransactionEntity::class,
        LPTokenSupplyEntity::class,
        PlatformEntity::class
    ],
    autoMigrations = [
        AutoMigration(from = 1, to = 2)
    ]

)
@TypeConverters(
    BaseConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun platformDao(): PlatformDao
    abstract fun portfolioCoinTransactionDao(): PortfolioCoinTransactionDao
    abstract fun portfolioLpTransactionDao(): PortfolioLpTransactionDao
    abstract fun coinDao(): CoinDao
    abstract fun lpTokenDao(): LPTokenDao
    abstract fun lpTokenSupplyDao(): LPTokenSupplyDao
}
