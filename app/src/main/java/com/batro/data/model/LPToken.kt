package com.batro.data.model

import androidx.annotation.Keep
import com.batro.data.database.entity.LPTokenEntity
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Represents a liquidity pool token, which is a token that represents a user's share of a liquidity pool.
 * The liquidity pool consists of two different currencies, represented by [coin0] and [coin1].
 *
 * @property contractAddress The address of the contract that manages the liquidity pool.
 * @property pid The ID of the pool in the protocol.
 * @property lpDecimals The number of decimal places used by the liquidity pool token.
 * @property currency0Decimals The number of decimal places used by the first currency in the pool.
 * @property currency1Decimals The number of decimal places used by the second currency in the pool.
 * @property coin0 The first currency in the liquidity pool.
 * @property coin1 The second currency in the liquidity pool.
 * @property serviceId The ID of the service that manages the liquidity pool.
 * @property chainId The ID of the chain where the liquidity pool is located.
 * @property poolExtra Extra information about the liquidity pool.
 * @see [LPTokenEntity]
 */

@Keep
data class LPToken(
    val contractAddress: String,
    val pid: Long?,
    val lpDecimals: Int = 18,
    val currency0Decimals: Int,
    val currency1Decimals: Int,
    @SerializedName("currency0")
    val coin0: Coin,
    @SerializedName("currency1")
    val coin1: Coin,
    val serviceId: String,
    val chainId: String,
    val poolExtra: LPTokenExtra?
) : Serializable {


    override fun toString(): String {
        return coin0.symbol.uppercase() + "-" + coin1.symbol.uppercase()
    }
}


