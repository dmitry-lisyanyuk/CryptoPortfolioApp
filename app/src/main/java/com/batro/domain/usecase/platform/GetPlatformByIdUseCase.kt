package com.batro.domain.usecase.platform

import com.batro.data.model.Platform
import com.batro.data.repository.platform.PlatformRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetPlatformByIdUseCase(
    private val repository: PlatformRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend operator fun invoke(id: String): Platform {
        return withContext(ioDispatcher) {
            repository.getPlatform(id)
        }
    }
}