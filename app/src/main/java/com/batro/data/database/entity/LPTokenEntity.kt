package com.batro.data.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.batro.data.model.LPTokenExtra

/**
 * Represents a liquidity pool token stored in a local database.
 * The liquidity pool consists of two different currencies, represented by [currency0Id] and [currency1Id].
 *
 * @property contractAddress The address of the contract that manages the liquidity pool.
 * @property pid The ID of the pool in the protocol.
 * @property lpDecimals The number of decimal places used by the liquidity pool token.
 * @property currency0Decimals The number of decimal places used by the first currency in the pool.
 * @property currency1Decimals The number of decimal places used by the second currency in the pool.
 * @property currency0Id The ID of the first currency in the liquidity pool.
 * @property currency1Id The ID of the second currency in the liquidity pool.
 * @property serviceId The ID of the service that manages the liquidity pool.
 * @property chainId The ID of the chain where the liquidity pool is located.
 * @property poolExtra Extra information about the liquidity pool.
 */

@Entity(tableName = "lp_token")
data class LPTokenEntity(
    @PrimaryKey
    val contractAddress: String,
    val pid: Long?,
    val lpDecimals: Int,
    val currency0Decimals: Int,
    val currency1Decimals: Int,
    val currency0Id: String,
    val currency1Id: String,
    val serviceId: String,
    val chainId: String,
    @Embedded
    val poolExtra: LPTokenExtra?
)
