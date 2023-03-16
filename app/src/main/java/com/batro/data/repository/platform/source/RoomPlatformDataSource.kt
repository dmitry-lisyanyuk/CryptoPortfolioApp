package com.batro.data.repository.platform.source

import com.batro.data.database.PlatformDao
import com.batro.data.database.entity.PlatformEntity

class RoomPlatformDataSource(private val platformDao: PlatformDao) : LocalPlatformDataSource {

    override suspend fun getPlatformList(): List<PlatformEntity> {
        return platformDao.getPlatformList()
    }

    override suspend fun getPlatform(id: String): PlatformEntity? {
        return platformDao.getPlatform(id)
    }

    override suspend fun insertPlatformList(platforms: List<PlatformEntity>) {
        platformDao.insertPlatformList(platforms)
    }

    override suspend fun insertPlatform(platform: PlatformEntity) {
        platformDao.insertPlatform(platform)
    }
}