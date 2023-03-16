package com.batro.data.model

import java.io.Serializable

/**
 * A data class that represents any extra information associated with an LP token.
 *
 * @property stakingContractAddress The contract address of the staking contract associated with the liquidity pool, if applicable.
 */

data class LPTokenExtra(
    val stakingContractAddress: String?
) : Serializable