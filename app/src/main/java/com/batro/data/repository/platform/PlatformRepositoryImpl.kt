package com.batro.data.repository.platform

import com.batro.data.mapper.toEntity
import com.batro.data.mapper.toPlatform
import com.batro.data.model.Platform
import com.batro.data.repository.platform.source.LocalPlatformDataSource
import com.batro.data.repository.platform.source.RemotePlatformDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlatformRepositoryImpl(
    private val remoteDataSource: RemotePlatformDataSource,
    private val localDataSource: LocalPlatformDataSource
) : PlatformRepository {

    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    private var cachedPlatforms: List<Platform>? = null


    override suspend fun getPlatformList(): List<Platform> {
        if (cachedPlatforms != null) {
            return cachedPlatforms!!
        }
        val localPlatforms = localDataSource.getPlatformList()
        val platforms = if (localPlatforms.isNotEmpty()) {
            localPlatforms.map { it.toPlatform() }
        } else {
            val remotePlatforms = remoteDataSource.getAssetPlatforms()
            scope.launch {
                localDataSource.insertPlatformList(remotePlatforms.map { it.toEntity() })
            }
            remotePlatforms
        }
        cachedPlatforms = platforms
        return platforms
    }

    override suspend fun getPlatform(id: String): Platform {
        val localPlatform = localDataSource.getPlatform(id)
        return if (localPlatform != null) {
            localPlatform.toPlatform()
        } else {
            val remotePlatform = remoteDataSource.getAssetPlatforms().find { it.id == id }
                ?: throw NoSuchElementException("platform with id=$id not found")
            scope.launch {
                localDataSource.insertPlatform(remotePlatform.toEntity())
            }
            remotePlatform
        }
    }
}