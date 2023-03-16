package com.batro.data.repository.platform.source

import com.batro.data.api.CryptoApi
import com.batro.data.model.Platform

class RetrofitPlatformDataSource(private val cryptoApi: CryptoApi) : RemotePlatformDataSource {

    override suspend fun getAssetPlatforms(): List<Platform> {
        return cryptoApi.getAssetPlatforms()
    }

}