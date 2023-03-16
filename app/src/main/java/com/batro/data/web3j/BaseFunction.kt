package com.batro.data.web3j

import org.web3j.abi.FunctionEncoder
import org.web3j.abi.FunctionReturnDecoder
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Function
import org.web3j.abi.datatypes.Type

abstract class BaseFunction<T>(
    name: String,
    var to: String,
    inputParameters: List<Type<*>>,
    outputParameters: List<TypeReference<*>>
) : Function(
    name,
    inputParameters,
    outputParameters
) {

    fun encode(): String {
        return FunctionEncoder.encode(this)
    }

    fun getResult(value: String): T {
        val e = FunctionReturnDecoder.decode(value, outputParameters)
        return mapResult(e)
    }

    abstract fun mapResult(output: List<Type<*>>): T


}