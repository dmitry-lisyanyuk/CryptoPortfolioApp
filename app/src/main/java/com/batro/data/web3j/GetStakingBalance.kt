package com.batro.data.web3j

import com.batro.data.model.PortfolioLpTransaction
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.Type
import org.web3j.abi.datatypes.generated.Uint256
import java.math.BigInteger

fun getStakingBalanceFun(lpInfo: PortfolioLpTransaction): BaseFunction<BigInteger>? {
    val stakingContractAddress =
        lpInfo.lpToken.poolExtra?.stakingContractAddress ?: when (lpInfo.lpToken.serviceId) {
            "pancake_swap" -> "0xa5f8c5dbd5f286960b9d90548680ae5ebff07652"
            else -> null
        } ?: return null
    val userAddress = lpInfo.address!!.value
    return when (lpInfo.lpToken.serviceId) {
        "ronin" -> GetStakingAmount(userAddress, stakingContractAddress)
        "pancake_swap" -> SPUserInfo(userAddress, stakingContractAddress, lpInfo.lpToken.pid ?: 0)
        else -> null
    }
}

class SPUserInfo(userAddress: String, toContract: String, pid: Long) : BaseFunction<BigInteger>(
    "userInfo",
    toContract,
    listOf(Uint256(pid), Address(userAddress)),
    listOf(
        TypeReference.create(Uint256::class.java),
        TypeReference.create(Uint256::class.java),
        TypeReference.create(Uint256::class.java)
    )
) {
    override fun mapResult(output: List<Type<*>>): BigInteger {
        return (output.getOrNull(0) as? Uint256)?.value ?: BigInteger.ZERO
    }

}

class GetStakingAmount(userAddress: String, stakingContract: String) : BaseFunction<BigInteger>(
    "getStakingAmount",
    stakingContract,
    listOf(Address(userAddress)),
    listOf(TypeReference.create(Uint256::class.java))
) {
    override fun mapResult(output: List<Type<*>>): BigInteger {
        return (output.getOrNull(0) as? Uint256)?.value ?: BigInteger.ZERO
    }

}