package com.batro.domain.usecase.platform

import com.batro.data.model.Platform
import com.batro.data.repository.platform.PlatformRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetPlatformListUseCase(private val repository: PlatformRepository) {

    suspend operator fun invoke(): List<Platform> {
        return withContext(Dispatchers.IO) {
            repository.getPlatformList()
        }
    }
}