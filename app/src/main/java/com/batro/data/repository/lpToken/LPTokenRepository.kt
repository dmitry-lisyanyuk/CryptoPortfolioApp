package com.batro.data.repository.lpToken

import com.batro.data.model.LPToken

/**
 * Interface for accessing LP token data.
 */
interface LPTokenRepository {

    /**
     * Retrieves a list of LP tokens for a given platform ID.
     *
     * @param platformId The platform ID to retrieve LP tokens for.
     * @return A list of [LPToken] objects.
     */
    suspend fun getLPTokens(platformId: String): List<LPToken>

}