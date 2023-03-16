package com.batro.domain

import com.batro.domain.usecase.*
import com.batro.domain.usecase.platform.GetPlatformByIdUseCase
import com.batro.domain.usecase.platform.GetPlatformListUseCase
import com.batro.domain.usecase.portfolio.AddPortfolioTransactionUseCase
import org.koin.dsl.module

val domainModule = module {

    factory { GetCoinsUseCase(get()) }
    factory { GetCoinUseCase(get()) }

    factory { GetLiquidityPoolsUseCase(get()) }
    factory { ObservePortfolioItemsUseCase(get(), get(), get(), get()) }
    factory { GetPortfolioTotalUseCase() }

    factory { GetCoinMarketChartUseCase(get()) }

    factory { ObserveCoinListPriceUseCase(get()) }
    factory { ObserveCoinPriceUseCase(get()) }

    factory { GetPlatformListUseCase(get()) }
    factory { GetPlatformByIdUseCase(get()) }
    factory { GetLPTokenByContractAddressUseCase(get(), get()) }

    factory { CalcPortfolioLPItemUseCase() }

    factory { AddPortfolioTransactionUseCase(get()) }

}
