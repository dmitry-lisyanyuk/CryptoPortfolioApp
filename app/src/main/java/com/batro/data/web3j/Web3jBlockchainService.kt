package com.batro.data.web3j


import io.reactivex.Single
import kotlinx.coroutines.suspendCancellableCoroutine
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameter

import org.web3j.protocol.core.methods.request.Transaction
import org.web3j.protocol.http.HttpService
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class Web3jBlockchainService(
    url:String,
    private val multiCallContract:String
) {

    private val web3HttpService = HttpService(url)

    private val web3 = Web3j.build(web3HttpService)

    private fun query(function: BaseFunction<*>):Single<String>{
        return Single.fromCallable {
            val r = web3.ethCall(
                Transaction.createEthCallTransaction(
                    null,
                    function.to,
                    function.encode()
                ),
                DefaultBlockParameter.valueOf("latest")
            ).send()
            if (r.error != null){
                throw Exception(r.error.message)
            }
            r.value
        }
    }
    private suspend fun query1(function: BaseFunction<*>):String{
        return suspendCancellableCoroutine { continuation ->
            val r = web3.ethCall(
                Transaction.createEthCallTransaction(
                    null,
                    function.to,
                    function.encode()
                ),
                DefaultBlockParameter.valueOf("latest")
            ).send()
            if (r.error != null){
                continuation.resumeWithException(Exception(r.error.message))
            }else{
                continuation.resume(r.value)
            }
        }
    }

    fun <T> queryFunction(function: BaseFunction<T>): Single<T> {
        return query(function).map { data ->
            function.getResult(data)
        }
    }
    fun <T> queryAggFunction(function: BaseAggregateFunction<T>): Single<T> {
        function.to = multiCallContract
        return query(function).map { data ->
            function.getAggregateResult(data)
        }
    }
    suspend fun <T> queryAggFunctionS(function: BaseAggregateFunction<T>): T {
        function.to = multiCallContract
        val result = query1(function)
        return function.getAggregateResult(result)
    }

}

