package com.batro.data.repository.lpToken

import com.batro.data.model.LPToken
import com.batro.data.repository.lpToken.source.LocalLPTokenDataSource
import com.batro.data.repository.lpToken.source.RemoteLPTokenDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class LPTokenRepositoryImpl(
    private val coroutineContext: CoroutineScope,
    private val remoteDataSource: RemoteLPTokenDataSource,
    private val localDataSource: LocalLPTokenDataSource
) : LPTokenRepository {

    override suspend fun getLPTokens(platformId: String): List<LPToken> {
        return try {
            remoteDataSource.getLPTokens(platformId).apply {
                coroutineContext.launch {
                    localDataSource.saveLPTokenList(this@apply)
                }
            }
        } catch (e: Exception) {
            localDataSource.getLPTokens(platformId)
        }
    }
}