package com.batro.domain.usecase.portfolio

import com.batro.data.model.PortfolioTransaction
import com.batro.data.repository.portfolioTransaction.PortfolioTransactionRepository


class AddPortfolioTransactionUseCase(
    private val portfolioTransactionRepository: PortfolioTransactionRepository
) {

    operator fun invoke(portfolioTransaction: PortfolioTransaction) {
        return portfolioTransactionRepository.insertPortfolioTransaction(portfolioTransaction)
    }
}