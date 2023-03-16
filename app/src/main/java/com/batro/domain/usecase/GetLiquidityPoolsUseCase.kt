package com.batro.domain.usecase

import com.batro.data.repository.lpToken.LPTokenRepository
import com.batro.data.model.LPToken
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetLiquidityPoolsUseCase(
    private val lpTokenRepository: LPTokenRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(platformId: String): List<LPToken> {
        return withContext(ioDispatcher) {
            lpTokenRepository.getLPTokens(platformId)
        }
    }
}