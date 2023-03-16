package com.batro.domain.usecase

import com.batro.data.repository.lpToken.source.LocalLPTokenDataSource
import com.batro.data.mapper.toLPToken
import com.batro.data.model.LPToken
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetLPTokenByContractAddressUseCase(
    private val localLPTokenDataSource: LocalLPTokenDataSource,
    private val getCoinUseCase: GetCoinUseCase,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend operator fun invoke(address: String): LPToken {
        return withContext(ioDispatcher) {
            val lPTokenEntity = localLPTokenDataSource.getLPTokenByAddress(address)
            lPTokenEntity.toLPToken(
                coin0 = getCoinUseCase(lPTokenEntity.currency0Id),
                coin1 = getCoinUseCase(lPTokenEntity.currency1Id)
            )
        }
    }

}