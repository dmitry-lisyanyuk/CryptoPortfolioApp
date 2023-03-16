package com.batro.data.web3j


data class CombineRequest(
    val functions: List<BaseFunction<*>>,
    var response: List<Any?>? = null,
    var meta: Any? = null
)

class CombineFunction(
    private val requests: List<CombineRequest>
) : BaseAggregateFunction<Unit>(
    requests.flatMap { request ->
        request.functions
    }
) {

    override fun mapAggregateResult(output: List<AggregateFunctionStruct>) {
        requests.mapIndexed { index, request ->
            val startIndex = requests.sumOf {
                if (requests.indexOf(it) < index) {
                    it.functions.size
                } else 0
            }
            request.response = request.functions.mapIndexed { index1, baseFunction ->
                baseFunction.getResult(output[startIndex + index1].getData())
            }
        }
    }
}