package com.batro.data.repository.lpToken.source

import com.batro.data.model.LPToken

/**
 * Interface for accessing remote LP token data.
 */
interface RemoteLPTokenDataSource {

    /**
     * Retrieves a list of LP tokens for a given platform ID from a remote data source.
     *
     * @param platformId The platform ID to retrieve LP tokens for.
     * @return A list of [LPToken] objects.
     */
    suspend fun getLPTokens(platformId: String): List<LPToken>
}