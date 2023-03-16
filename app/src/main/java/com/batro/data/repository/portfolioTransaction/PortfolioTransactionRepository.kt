package com.batro.data.repository.portfolioTransaction

import com.batro.data.model.PortfolioCoinTransaction
import com.batro.data.model.PortfolioLpTransaction
import com.batro.data.model.PortfolioTransaction
import kotlinx.coroutines.flow.Flow

/**
 * A repository for managing portfolio transactions. This interface provides methods for accessing
 * and manipulating portfolio transactions and their associated data.
 */
interface PortfolioTransactionRepository {

    /**
     * Returns a Flow of all portfolio transactions.
     * @return A Flow emitting a list of [PortfolioTransaction].
     */
    fun portfolioTransactionFlow(): Flow<List<PortfolioTransaction>>

    /**
     * Returns a Flow of all portfolio coin transactions filtered by coin ID.
     * @param id The coin ID to filter by.
     * @return A Flow emitting a list of [PortfolioCoinTransaction].
     */
    fun portfolioCoinTransactionFlowByCoinId(id: String): Flow<List<PortfolioCoinTransaction>>

    /**
     * Returns a Flow of all portfolio LP transactions filtered by LP contract address.
     * @param id The LP token contract address to filter by.
     * @return A Flow emitting a list of [PortfolioLpTransaction].
     */
    fun portfolioLpTransactionFlowByLpContractAddress(id: String): Flow<List<PortfolioLpTransaction>>

    /**
     * Inserts a new portfolio transaction into the database.
     * @param portfolioTransaction The [PortfolioTransaction] to insert.
     */
    fun insertPortfolioTransaction(portfolioTransaction: PortfolioTransaction)

    /**
     * Updates an existing portfolio transaction in the database.
     * @param portfolioTransaction The [PortfolioTransaction] to update.
     */
    fun updatePortfolioTransaction(portfolioTransaction: PortfolioTransaction)

    /**
     * Retrieves a single coin transaction from the database by ID.
     * @param id The ID of the [PortfolioCoinTransaction] to retrieve.
     * @return The retrieved [PortfolioCoinTransaction].
     */
    suspend fun getCoinTransactionById(id: Int): PortfolioCoinTransaction

    /**
     * Retrieves a single LP transaction from the database by ID.
     * @param id The ID of the [PortfolioLpTransaction] to retrieve.
     * @return The retrieved [PortfolioLpTransaction].
     */
    suspend fun getLpTransactionById(id: Int): PortfolioLpTransaction

    /**
     * Deletes a single coin transaction from the database by ID.
     * @param id The ID of the [PortfolioCoinTransaction] to delete.
     */
    fun deletePortfolioCoinTransactionById(id: Int)

    /**
     * Deletes a single LP transaction from the database by ID [PortfolioLpTransaction.coinInfoId].
     * @param id The ID of the [PortfolioLpTransaction] to delete.
     */
    fun deletePortfolioLpTransactionById(id: Int)
}