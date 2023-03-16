package com.batro.data.repository.portfolioTransaction

import androidx.room.withTransaction
import com.batro.data.repository.coin.source.LocalCoinDataSource
import com.batro.data.model.*
import com.batro.data.database.AppDatabase
import com.batro.data.database.entity.*
import com.batro.data.mapper.*
import com.batro.data.repository.lpToken.source.LocalLPTokenDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PortfolioTransactionRepositoryImpl(
    private val coroutineContext: CoroutineScope,
    private val localCoinDataSource: LocalCoinDataSource,
    private val localLPTokenDataSource: LocalLPTokenDataSource,
    val db: AppDatabase
) : PortfolioTransactionRepository {

    private val portfolioCoinTransactionDao = db.portfolioCoinTransactionDao()

    private val portfolioLpTransactionDao = db.portfolioLpTransactionDao()

    override fun portfolioCoinTransactionFlowByCoinId(id: String): Flow<List<PortfolioCoinTransaction>> {
        return portfolioCoinTransactionDao.getListByCoinId(id).mapCoinTx()
    }

    override fun portfolioLpTransactionFlowByLpContractAddress(id: String): Flow<List<PortfolioLpTransaction>> {
        return portfolioLpTransactionDao.getListByContractAddress(id).mapLpTx()
    }

    override fun portfolioTransactionFlow(): Flow<List<PortfolioTransaction>> {
        val portfolioCoinTransactionFlow = portfolioCoinTransactionDao.getList().mapCoinTx()
        val portfolioLpTransactionFlow = portfolioLpTransactionDao.getList().mapLpTx()
        return portfolioCoinTransactionFlow.combine(portfolioLpTransactionFlow) { a, b ->
            a.plus(b)
        }
    }

    override fun insertPortfolioTransaction(portfolioTransaction: PortfolioTransaction) {
        coroutineContext.launch {
            when (portfolioTransaction) {
                is PortfolioCoinTransaction -> {
                    db.withTransaction {
                        val transaction = portfolioTransaction.toEntity()
                        localCoinDataSource.saveCoin(portfolioTransaction.coin)
                        portfolioCoinTransactionDao.insertTransaction(transaction)
                    }
                }
                is PortfolioLpTransaction -> {
                    db.withTransaction {
                        val transaction = portfolioTransaction.toEntity()
                        localLPTokenDataSource.saveLPToken(portfolioTransaction.lpToken)
                        portfolioLpTransactionDao.insertTransaction(transaction)
                    }
                }
            }
        }
    }

    override fun updatePortfolioTransaction(portfolioTransaction: PortfolioTransaction) {
        coroutineContext.launch {
            when (portfolioTransaction) {
                is PortfolioCoinTransaction -> {
                    val transaction = portfolioTransaction.toEntity()
                    portfolioCoinTransactionDao.updateTransaction(transaction)
                }
                is PortfolioLpTransaction -> {
                    val transaction = portfolioTransaction.toEntity()
                    portfolioLpTransactionDao.updateTransaction(transaction)
                }
            }
        }
    }

    private suspend fun getCoinById(id: String): Coin {
        return localCoinDataSource.getCoin(id).toCoin()
    }

    override suspend fun getCoinTransactionById(id: Int): PortfolioCoinTransaction {
        val tx = portfolioCoinTransactionDao.getTransactionById(id)
        val coin = getCoinById(tx.coinId)
        return PortfolioCoinTransaction(
            coin,
            tx.amount,
            tx.price,
            tx.note,
            tx.coinInfoId
        )
    }

    override suspend fun getLpTransactionById(id: Int): PortfolioLpTransaction {
        val tx = db.portfolioLpTransactionDao().getLpTransactionById(id)
        val lpToken = getLpTokenByAddress(tx.lpTokenAddress)
        return tx.toPortfolioLpTransaction(lpToken)
    }

    private suspend fun getLpTokenByAddress(address: String): LPToken {
        val lPTokenEntity = localLPTokenDataSource.getLPTokenByAddress(address)
        return lPTokenEntity.toLPToken(
            coin0 = getCoinById(lPTokenEntity.currency0Id),
            coin1 = getCoinById(lPTokenEntity.currency1Id)
        )
    }

    override fun deletePortfolioCoinTransactionById(id: Int) {
        coroutineContext.launch {
            db.portfolioCoinTransactionDao().deleteById(id)
        }
    }

    override fun deletePortfolioLpTransactionById(id: Int) {
        coroutineContext.launch {
            portfolioLpTransactionDao.deleteById(id)
        }
    }

    private fun Flow<List<PortfolioCoinTransactionEntity>>.mapCoinTx(): Flow<List<PortfolioCoinTransaction>> {
        return map { txList ->
            if (txList.isEmpty()) return@map listOf()
            val coins = localCoinDataSource.getCoinList(txList.map { it.coinId })
            txList.map { tx ->
                val coin = coins.first { it.coingeckoId == tx.coinId }.toCoin()
                tx.toPortfolioCoinTransaction(coin)
            }
        }
    }

    private fun Flow<List<PortfolioLpTransactionEntity>>.mapLpTx(): Flow<List<PortfolioLpTransaction>> {
        return map { txList ->
            if (txList.isEmpty()) return@map listOf()
            val lpList =
                localLPTokenDataSource.getLPTokens(txList.map { it.lpTokenAddress }.toSet())
            txList.map { tx ->
                val lp = lpList.first { it.contractAddress == tx.lpTokenAddress }
                tx.toPortfolioLpTransaction(lp)
            }
        }
    }


}