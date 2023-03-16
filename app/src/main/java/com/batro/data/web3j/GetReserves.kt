package com.batro.data.web3j

import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Type
import org.web3j.abi.datatypes.generated.Uint112
import org.web3j.abi.datatypes.generated.Uint32
import java.math.BigInteger

data class ReservesResponse(
    val reserve0: BigInteger,
    val reserve1: BigInteger
)

class GetReserves(toContract: String) : BaseFunction<ReservesResponse>(
    "getReserves",
    toContract,
    listOf(),
    listOf(
        TypeReference.create(Uint112::class.java),
        TypeReference.create(Uint112::class.java),
        TypeReference.create(Uint32::class.java)
    )
) {
    override fun mapResult(output: List<Type<*>>): ReservesResponse {
        val reserve0 = (output[0] as Uint112).value
        val reserve1 = (output[1] as Uint112).value
        return ReservesResponse(reserve0, reserve1)
    }

}