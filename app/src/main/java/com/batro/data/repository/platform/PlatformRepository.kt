package com.batro.data.repository.platform

import com.batro.data.model.Platform

/**
 * Interface for platform repository.
 */
interface PlatformRepository {

    /**
     * Returns a list of all platforms.
     *
     * @return List of [Platform].
     */
    suspend fun getPlatformList(): List<Platform>

    /**
     * Returns the platform with the given id.
     *
     * @param id Platform id.
     * @return [Platform] with the given id.
     * @throws NoSuchElementException if the platform with the given id is not found.
     */
    suspend fun getPlatform(id: String): Platform
}







