package com.batro.data.repository.lpTokenSupply

import android.annotation.SuppressLint
import com.batro.data.database.AppDatabase
import com.batro.data.database.entity.LPTokenEntity
import com.batro.data.database.entity.LPTokenSupplyEntity
import com.batro.data.repository.lpToken.source.LocalLPTokenDataSource
import com.batro.data.repository.platform.PlatformRepository
import com.batro.util.extension.interval
import com.batro.data.web3j.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.math.BigInteger

@Suppress("EXPERIMENTAL_IS_NOT_ENABLED")
@OptIn(ExperimentalCoroutinesApi::class)
@SuppressLint("CheckResult")
class LPTokenSupplyRepository : KoinComponent {

    private val db: AppDatabase by inject()

    private val platformRepository: PlatformRepository by inject()

    private val localLPTokenDataSource: LocalLPTokenDataSource by inject()

    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    init {
        db.portfolioLpTransactionDao().getList().flatMapLatest { portfolioTransactions ->
            val portfolioLp = portfolioTransactions.map { it.lpTokenAddress }.toSet()
            if (portfolioLp.isEmpty()) return@flatMapLatest flowOf()
            val lpTokens = localLPTokenDataSource.getLPTokensEntity(portfolioLp)
            if (lpTokens.isEmpty()) return@flatMapLatest flowOf()
            interval(20_000).map {
                val platforms = platformRepository.getPlatformList()
                val lpSupplyList = mutableListOf<LPTokenSupplyEntity>()
                lpTokens.groupBy { it.chainId }.entries.forEach { entry ->
                    val lpTokenList = entry.value
                    val service = platforms.find { it.id == entry.key }?.let {
                        Web3jBlockchainService(it.chainRpcUrl, it.multicallContract)
                    } ?: return@forEach

                    val funList = lpTokenList.map { lpToken ->
                        CombineRequest(
                            listOfNotNull(
                                TotalSupply(lpToken.contractAddress),
                                GetReserves(lpToken.contractAddress)
                            ), meta = lpToken
                        )
                    }
                    service.queryAggFunctionS(CombineFunction(funList))
                    funList.forEach { request ->
                        val totalSupply = request.response!![0] as BigInteger
                        val reservesResponse = request.response!![1] as ReservesResponse
                        lpSupplyList.add(
                            LPTokenSupplyEntity(
                                (request.meta as LPTokenEntity).contractAddress,
                                reservesResponse.reserve0,
                                reservesResponse.reserve1,
                                totalSupply
                            )
                        )
                    }
                }
                if (lpSupplyList.isNotEmpty()) {
                    db.lpTokenSupplyDao().insertLPTokenSupplyList(lpSupplyList)
                }
            }.retryWhen { _, _ ->
                delay(7000)
                true
            }
        }.launchIn(scope)
    }

    fun lpTokenSupplyFlow(): Flow<List<LPTokenSupplyEntity>> {
        return db.lpTokenSupplyDao().getLPTokenSupplyFlow()
    }
}


//val funList = lpTokenList.map { lpInfo ->
//    CombineRequest(
//        listOfNotNull(
//            TotalSupply(lpInfo.lpToken.contractAddress),
//            GetReserves(lpInfo.lpToken.contractAddress),
//            GetBalance(lpInfo.address!!.value, lpInfo.lpToken.contractAddress),
//            getStakingBalanceFun(lpInfo)
//        ), meta = lpInfo
//    )
//}
//service.queryAggFunction(CombineFunction(funList)).blockingGet()
//funList.forEach { request ->
//    val totalSupply = request.response!![0] as BigInteger
//    val reservesResponse = request.response!![1] as ReservesResponse
//    val userBalance  = request.response!![2] as BigInteger
//    val stakedBalance  = request.response?.getOrNull(3) as? BigInteger ?: BigInteger.ZERO
//    val item = calcPortfolioLPItem(
//        request.meta as PortfolioLpTransaction,
//        userBalance.plus(stakedBalance),
//        totalSupply,
//        reservesResponse.reserve0,
//        reservesResponse.reserve1,
//        prices
//    )
//    dataList.add(item)
//}