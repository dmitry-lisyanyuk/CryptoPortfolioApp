package com.batro.data.repo

import com.batro.data.database.entity.PlatformEntity
import com.batro.data.model.Platform
import com.batro.data.repository.platform.PlatformRepositoryImpl
import com.batro.data.repository.platform.source.LocalPlatformDataSource
import com.batro.data.repository.platform.PlatformRepository
import com.batro.data.repository.platform.source.RemotePlatformDataSource
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class PlatformRepositoryTest {

    private lateinit var repository: PlatformRepository
    private lateinit var remoteDataSource: FakeRemotePlatformDataSource
    private lateinit var localDataSource: FakeLocalPlatformDataSource

    @Before
    fun setUp() {
        remoteDataSource = FakeRemotePlatformDataSource()
        localDataSource = FakeLocalPlatformDataSource()
        repository = PlatformRepositoryImpl(remoteDataSource, localDataSource)
    }

    @Test
    fun `test get asset platforms`() = runBlocking {
        val expectedPlatforms = listOf(
            Platform(
                id = "1",
                name = "Platform 1",
                chainIdentifier = 1,
                chainRpcUrl = "https://platform1.com/rpc",
                multicallContract = "0x123",
                icon = "https://platform1.com/icon.png"
            ),
            Platform(
                id = "2",
                name = "Platform 2",
                chainIdentifier = 2,
                chainRpcUrl = "https://platform2.com/rpc",
                multicallContract = "0x456",
                icon = "https://platform2.com/icon.png"
            )
        )

        val result = repository.getPlatformList()

        assertEquals(expectedPlatforms, result)
    }

}

class FakeRemotePlatformDataSource : RemotePlatformDataSource {

    override suspend fun getAssetPlatforms(): List<Platform> {
        return listOf(
            Platform(
                id = "1",
                name = "Platform 1",
                chainIdentifier = 1,
                chainRpcUrl = "https://platform1.com/rpc",
                multicallContract = "0x123",
                icon = "https://platform1.com/icon.png"
            ),
            Platform(
                id = "2",
                name = "Platform 2",
                chainIdentifier = 2,
                chainRpcUrl = "https://platform2.com/rpc",
                multicallContract = "0x456",
                icon = "https://platform2.com/icon.png"
            )
        )
    }
}

class FakeLocalPlatformDataSource : LocalPlatformDataSource {

    val platforms = mutableListOf(
        PlatformEntity(
            id = "1",
            name = "Platform 1",
            chainIdentifier = 1,
            chainRpcUrl = "https://platform1.com/rpc",
            multicallContract = "0x123",
            icon = "https://platform1.com/icon.png"
        ),
        PlatformEntity(
            id = "2",
            name = "Platform 2",
            chainIdentifier = 2,
            chainRpcUrl = "https://platform2.com/rpc",
            multicallContract = "0x456",
            icon = "https://platform2.com/icon.png"
        )
    )

    override suspend fun getPlatformList(): List<PlatformEntity> {
        return platforms
    }

    override suspend fun getPlatform(id: String): PlatformEntity {
        return platforms.firstOrNull { it.id == id } ?: throw NoSuchElementException("Platform not found")
    }

    override suspend fun insertPlatformList(platforms: List<PlatformEntity>) {
        this.platforms.addAll(platforms)
    }

    override suspend fun insertPlatform(platform: PlatformEntity) {
        this.platforms.add(platform)
    }
}