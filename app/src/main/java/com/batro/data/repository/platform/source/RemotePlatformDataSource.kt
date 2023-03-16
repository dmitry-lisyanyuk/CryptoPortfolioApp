package com.batro.data.repository.platform.source

import com.batro.data.model.Platform

/**
 * Interface for remote platform data source.
 */
interface RemotePlatformDataSource {

    /**
     * Returns a list of all asset platforms available remotely.
     *
     * @return List of [Platform].
     */
    suspend fun getAssetPlatforms(): List<Platform>

}