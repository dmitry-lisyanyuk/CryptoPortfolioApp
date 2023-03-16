package com.batro.ui.screen.addCoinTransaction

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel
import com.batro.ui.screen.selectCoin.COIN_EXTRA
import com.batro.util.extension.GetOnceResult


@Composable
fun AddTransactionRoute(
    coinId: String?,
    navController: NavHostController = rememberNavController(),
    viewModel: AddCoinTransactionViewModel = mavericksViewModel(argsFactory = {
        CurrencyAddIdArgument(coinId)
    })
) {
    navController.GetOnceResult(COIN_EXTRA, viewModel::setCurrency)
    val state = viewModel.collectAsState()
    AddCoinTransactionScreen(
        state.value,
        onChangeCoin = {
            navController.navigate("selectCurrency")
        },
        onAddTransaction = {
            viewModel.addPortfolioInfo(it)
            navController.navigateUp()
        }
    )
}