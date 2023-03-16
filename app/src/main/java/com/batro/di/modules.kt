package com.batro.di

import androidx.room.Room
import com.batro.BuildConfig
import com.batro.data.api.CoingecoApi
import com.batro.data.api.CryptoApi
import com.batro.data.repository.coinPrice.source.CoinGecoCoinPriceDataSource
import com.batro.data.repository.coinPrice.CoinPriceService
import com.batro.data.repository.coinPrice.DefaultCoinPriceService
import com.batro.data.database.AppDatabase
import com.batro.data.repository.coin.*
import com.batro.data.repository.coin.source.LocalCoinDataSource
import com.batro.data.repository.coin.source.LocalCoinDataSourceImpl
import com.batro.data.repository.coin.source.RemoteCoinDataSourceImpl
import com.batro.data.repository.platform.source.RetrofitPlatformDataSource
import com.batro.data.repository.platform.source.RoomPlatformDataSource
import com.batro.data.repository.portfolioTransaction.PortfolioTransactionRepository
import com.batro.data.repository.portfolioTransaction.PortfolioTransactionRepositoryImpl
import com.batro.data.repository.lpTokenSupply.LPTokenSupplyRepository
import com.batro.data.repository.lpToken.*
import com.batro.data.repository.lpToken.source.LocalLPTokenDataSource
import com.batro.data.repository.lpToken.source.LocalLPTokenDataSourceImpl
import com.batro.data.repository.lpToken.source.RemoteLPTokenDataSourceImpl
import com.batro.data.repository.platform.PlatformRepository
import com.batro.data.repository.platform.PlatformRepositoryImpl
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val baseModule = module {

    single {
        Room.databaseBuilder(androidApplication(), AppDatabase::class.java, "crypto_db").build()
    }
    single { get<AppDatabase>().platformDao() }
    // App CoroutineScope
    single {
        CoroutineScope(Dispatchers.IO + SupervisorJob())
    }
    single<LocalCoinDataSource> {
        LocalCoinDataSourceImpl(get<AppDatabase>().coinDao())
    }
    single<LocalLPTokenDataSource> {
        LocalLPTokenDataSourceImpl(get(), get<AppDatabase>().lpTokenDao(), get())
    }
    single<PlatformRepository> {
        PlatformRepositoryImpl(
            remoteDataSource = RetrofitPlatformDataSource(get()),
            localDataSource = RoomPlatformDataSource(get())
        )
    }
    single<CoinRepository> {
        CoinRepositoryImpl(
            coroutineContext = get(),
            remoteDataSource = RemoteCoinDataSourceImpl(get()),
            localDataSource = get()
        )
    }

    single<CoinPriceService> {
        DefaultCoinPriceService(
            coroutineContext = get(),
            dataSource = CoinGecoCoinPriceDataSource(get())
        )
    }
    single<PortfolioTransactionRepository> {
        PortfolioTransactionRepositoryImpl(
            coroutineContext = get(),
            localCoinDataSource = get(),
            localLPTokenDataSource = get(),
            db = get()
        )
    }
    single<LPTokenRepository> {
        LPTokenRepositoryImpl(
            coroutineContext = get(),
            remoteDataSource = RemoteLPTokenDataSourceImpl(get()),
            localDataSource = get()
        )
    }

    single {
        LPTokenSupplyRepository()
    }

    single {
        OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                val interceptor = HttpLoggingInterceptor()
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                addInterceptor(interceptor)
            }
        }.build()
    }

    single<CryptoApi> {
        Retrofit.Builder().baseUrl("https://cryptoapp-call.lm.r.appspot.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
            .create(CryptoApi::class.java)
    }
    single<CoingecoApi> {
        Retrofit.Builder().baseUrl("https://api.coingecko.com/api/v3/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
            .create(CoingecoApi::class.java)
    }
}