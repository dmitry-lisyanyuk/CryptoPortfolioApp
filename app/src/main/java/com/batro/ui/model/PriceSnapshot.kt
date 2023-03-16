package com.batro.ui.model


/**
 * A data class representing a snapshot of a cryptocurrency price at a given timestamp.
 *
 * @param timestamp The Unix timestamp of the snapshot.
 * @param price The price of the cryptocurrency at the time of the snapshot.
 */

data class PriceSnapshot(
    val timestamp: Long,
    val price: Float
)
