package com.batro.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigInteger


/**
 * This class represents an entity that holds information about a liquidity pool token supply.
 * It is used to store data in a database table named "lp_token_supply".
 * @property contractAddress The contract address of the liquidity pool token.
 * @property currency0Amount The amount of the first currency in the liquidity pool.
 * @property currency1Amount The amount of the second currency in the liquidity pool.
 * @property totalSupply The total supply of the liquidity pool token.
 */

@Entity(tableName = "lp_token_supply")
data class LPTokenSupplyEntity(
    @PrimaryKey
    val contractAddress: String,
    val currency0Amount: BigInteger,
    val currency1Amount: BigInteger,
    val totalSupply: BigInteger
)
