package com.batro.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.batro.data.model.Coin

/**
 * Represents a coin entity that can be stored in a database.
 *
 * @param coingeckoId The unique identifier for the coin on the CoinGecko platform. This ID is used
 *                    to retrieve information about the coin from the CoinGecko API. This field is
 *                    marked with the `@PrimaryKey` annotation, which indicates that it is the
 *                    primary key for the corresponding database table. In other words, the value of
 *                    this field must be unique for each record in the table.
 * @param name The name of the coin.
 * @param symbol The ticker symbol for the coin.
 * @param icon The URL for the icon image associated with the coin. This image is typically
 *             displayed in user interfaces to help users identify the coin.
 * @param marketCap The market capitalization of the coin, expressed in US dollars.
 * @see Coin
 */

@Entity(tableName = "coin")
data class CoinEntity(
    @PrimaryKey
    val coingeckoId: String,
    val name: String,
    val symbol: String,
    val icon: String,
    val marketCap: Long
)