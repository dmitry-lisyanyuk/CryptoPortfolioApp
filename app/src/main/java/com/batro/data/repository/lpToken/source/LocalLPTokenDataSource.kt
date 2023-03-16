package com.batro.data.repository.lpToken.source

import com.batro.data.database.entity.LPTokenEntity
import com.batro.data.model.LPToken

/**
 * Interface for accessing local LP token data.
 */
interface LocalLPTokenDataSource {

    /**
     * Retrieves a list of LP tokens for a given platform ID.
     *
     * @param platformId The platform ID to retrieve LP tokens for.
     * @return A list of [LPToken] objects.
     */
    suspend fun getLPTokens(platformId: String): List<LPToken>

    /**
     * Retrieves a list of LP tokens for a given set of contract addresses.
     *
     * @param contractAddressSet The set of contract addresses to retrieve LP tokens for.
     * @return A list of [LPToken] objects.
     */
    suspend fun getLPTokens(contractAddressSet: Set<String>): List<LPToken>

    /**
     * Retrieves a list of LP token entities for a given set of contract addresses.
     *
     * @param contractAddressSet The set of contract addresses to retrieve LP tokens entities for.
     * @return A list of [LPTokenEntity] objects.
     */
    suspend fun getLPTokensEntity(contractAddressSet: Set<String>): List<LPTokenEntity>

    /**
     * Saves an LP token to the local data source.
     *
     * @param lpToken The LP token to save.
     */
    suspend fun saveLPToken(lpToken: LPToken)

    /**
     * Saves a list of LP tokens to the local data source.
     *
     * @param lpTokens The list of LP tokens to save.
     */
    suspend fun saveLPTokenList(lpTokens: List<LPToken>)

    /**
     * Retrieves an LP token entity for a given contract address.
     *
     * @param address The contract address to retrieve the LP token entity for.
     * @return An [LPTokenEntity] object.
     */
    suspend fun getLPTokenByAddress(address: String): LPTokenEntity
}