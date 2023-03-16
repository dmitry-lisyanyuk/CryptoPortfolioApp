package com.batro.data.web3j

import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.Type
import org.web3j.abi.datatypes.generated.Uint256
import java.math.BigInteger

class GetBalance(userAddress: String, toContract: String) : BaseFunction<BigInteger>(
    "balanceOf",
    toContract,
    listOf(Address(userAddress)),
    listOf(TypeReference.create(Uint256::class.java))
) {
    override fun mapResult(output: List<Type<*>>): BigInteger {
        return (output[0] as Uint256).value
    }

}