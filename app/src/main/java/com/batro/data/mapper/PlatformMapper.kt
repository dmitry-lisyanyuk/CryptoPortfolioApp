package com.batro.data.mapper

import com.batro.data.database.entity.PlatformEntity
import com.batro.data.model.Platform

fun Platform.toEntity(): PlatformEntity {
    return PlatformEntity(
        id = id,
        name = name,
        chainIdentifier = chainIdentifier,
        chainRpcUrl = chainRpcUrl,
        multicallContract = multicallContract,
        icon = icon
    )
}

fun PlatformEntity.toPlatform(): Platform {
    return Platform(
        id = id,
        name = name,
        chainIdentifier = chainIdentifier,
        chainRpcUrl = chainRpcUrl,
        multicallContract = multicallContract,
        icon = icon
    )
}