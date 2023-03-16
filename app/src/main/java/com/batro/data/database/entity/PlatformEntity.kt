package com.batro.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.batro.data.model.Platform

/**
 * Represents a platform entity that can be stored in a database.
 *
 * @param id The unique identifier for the platform. This field is marked with the `@PrimaryKey`
 *           annotation, which indicates that it is the primary key for the corresponding database
 *           table. In other words, the value of this field must be unique for each record in the table.
 * @param name The name of the platform. This field stores a string value that represents the name
 *             of the platform.
 * @param chainIdentifier An integer value that identifies the blockchain network associated with
 *                        the platform. This value is used to distinguish between different
 *                        blockchain networks that a platform may support.
 * @param chainRpcUrl The URL for the RPC endpoint of the blockchain network associated with the
 *                    platform. This URL is used to connect to the blockchain network and query
 *                    information from it.
 * @param multicallContract The address of the Multicall contract for the platform. The Multicall
 *                          contract is a smart contract that allows multiple calls to be made to
 *                          the blockchain network in a single transaction, which can improve
 *                          performance and reduce costs.
 * @param icon The URL for the icon image associated with the platform. This image is typically
 *             displayed in user interfaces to help users identify the platform.
 * @see Platform
 */

@Entity(tableName = "platform")
data class PlatformEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val chainIdentifier: Int,
    val chainRpcUrl: String,
    val multicallContract: String,
    val icon: String
)
