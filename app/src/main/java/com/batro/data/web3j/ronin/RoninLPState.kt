package com.batro.data.web3j.ronin

import com.batro.data.web3j.*
import org.web3j.abi.datatypes.Address
import java.math.BigInteger


data class GetRoninLPSResponse(
    val totalSupply:BigInteger,
    val reservesResponse: ReservesResponse,
    val stakingAmount: BigInteger,
)

data class RoninLPStateRequest(val lpContract:String, val userAddress: Address, val stakingContract:String?)

class RoninLPStateFunction(private val requests:List<RoninLPStateRequest>) : BaseAggregateFunction<List<GetRoninLPSResponse>>(
    requests.flatMap { request ->
        listOf(
            TotalSupply(request.lpContract),
            GetReserves(request.lpContract),
            GetBalance(request.userAddress.value, request.lpContract)
//            GetStakingAmount(request.userAddress,request.stakingContract ?: "0x0")
        )
    },
    "0xc76d0d0d3aa608190f78db02bf2f5aef374fc0b9"
) {

    override fun mapAggregateResult(output: List<AggregateFunctionStruct>): List<GetRoninLPSResponse> {
        return requests.mapIndexed { index, _ ->
            val startIndex = index * 3
            GetRoninLPSResponse(
                funList[startIndex + 0].getResult(output[startIndex + 0].getData()) as BigInteger,
                funList[startIndex + 1].getResult(output[startIndex + 1].getData()) as ReservesResponse,
                funList[startIndex + 2].getResult(output[startIndex + 2].getData()) as? BigInteger ?: BigInteger.ZERO
            )
        }

    }
}