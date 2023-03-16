package com.batro.data.repository.platform.source

import com.batro.data.database.entity.PlatformEntity

/**
 * Interface for local platform data source.
 */
interface LocalPlatformDataSource {

    /**
     * Returns a list of all platforms available locally.
     *
     * @return List of [PlatformEntity].
     */
    suspend fun getPlatformList(): List<PlatformEntity>

    /**
     * Returns the platform with the given id.
     *
     * @param id Platform id.
     * @return [PlatformEntity] with the given id, or null if not found.
     */
    suspend fun getPlatform(id: String): PlatformEntity?

    /**
     * Inserts a list of platforms into the local data source.
     *
     * @param platforms List of [PlatformEntity] to insert.
     */
    suspend fun insertPlatformList(platforms: List<PlatformEntity>)

    /**
     * Inserts a single platform into the local data source.
     *
     * @param platform [PlatformEntity] to insert.
     */
    suspend fun insertPlatform(platform: PlatformEntity)
}