package com.batro.data.model

import androidx.annotation.Keep
import com.batro.data.database.entity.CoinEntity
import java.io.Serializable

/**
 * Represents a coin in the cryptocurrency market.
 *
 * @param coingeckoId The unique identifier for the coin on the CoinGecko platform. This ID is used
 *                    to retrieve information about the coin from the CoinGecko API.
 * @param name The name of the coin.
 * @param symbol The ticker symbol for the coin.
 * @param icon The URL for the icon image associated with the coin. This image is typically
 *             displayed in user interfaces to help users identify the coin.
 * @param marketCap The market capitalization of the coin, expressed in US dollars.
 * @see CoinEntity
 */

@Keep
data class Coin(
    val coingeckoId: String,
    val name: String,
    val symbol: String,
    val icon: String,
    val marketCap: Long
) : Serializable