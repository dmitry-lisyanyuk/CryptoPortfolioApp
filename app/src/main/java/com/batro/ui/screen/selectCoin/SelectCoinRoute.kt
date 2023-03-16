package com.batro.ui.screen.selectCoin

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel

import com.batro.util.extension.setResult


const val COIN_EXTRA = "coin"

@Composable
fun SelectCoinRoute(
    navController: NavHostController = rememberNavController(),
    viewModel: SelectCoinViewModel = mavericksViewModel()
) {
    val state = viewModel.collectAsState()

    SelectCoinScreen(
        state.value,
        onCoinSelected = { coin ->
            navController.setResult(COIN_EXTRA, coin)
            navController.navigateUp()
        },
        onSearch = viewModel::search,
        onScrolledLast = {
            viewModel.loadNext()
        }
    )
}