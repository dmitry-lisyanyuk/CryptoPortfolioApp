package com.batro.data.web3j

import org.bouncycastle.util.encoders.Hex
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.*



class AggregateFunctionStruct(private val bool: Bool, private val bytesType: DynamicBytes):DynamicStruct(listOf(bool, bytesType)){

    @OptIn(ExperimentalUnsignedTypes::class)
    fun getData() = "0x" + bytesType.value.asUByteArray().joinToString("") { it.toString(16).padStart(2, '0') }
}

abstract class BaseAggregateFunction<T>(
    val funList:List<BaseFunction<*>>,
    multicallContract:String = "0x0"
) : BaseFunction<List<AggregateFunctionStruct>>(
    "tryAggregate",
    multicallContract,
    listOf(
        Bool(false),
        DynamicArray(DynamicStruct::class.java, *funList.toDynamicStructList().toTypedArray())
    ),
    listOf(object : TypeReference<DynamicArray<AggregateFunctionStruct>>() {})
) {

    @Suppress("UNCHECKED_CAST")
    final override fun mapResult(output: List<Type<*>>): List<AggregateFunctionStruct> {
        return (output[0] as DynamicArray<AggregateFunctionStruct>).value
    }

    abstract fun mapAggregateResult(output: List<AggregateFunctionStruct>): T

    fun getAggregateResult(value: String): T {
        return mapAggregateResult(getResult(value))
    }


}

private fun List<BaseFunction<*>>.toDynamicStructList():List<DynamicStruct>{
    return map {
        DynamicStruct(
            Address(it.to),
            DynamicBytes(Hex.decode(it.encode().substring(2).toByteArray()))
        )
    }
}