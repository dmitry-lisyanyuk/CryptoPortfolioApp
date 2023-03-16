package com.batro.ui.screen.delailsPorfolio.coin

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel
import com.batro.ui.view.BaseAppScaffold

@Composable
fun DetailsCoinRoute(
    coinId: String,
    navController: NavHostController = rememberNavController(),
    viewModel: DetailsCoinViewModel = mavericksViewModel(argsFactory = { DetailsIdArgument(coinId) })
) {

    val state = viewModel.collectAsState()

    if (state.value.detailsCoinInfo?.txList?.isEmpty() == true) {
        navController.navigateUp()
        return
    }
    BaseAppScaffold(
        title = state.value.coinRequest.invoke()?.name ?: ""
    ) {
        DetailsCoinScreen(
            state.value,
            onAddNewItemClicked = {
                val coin = state.value.coinRequest.invoke() ?: return@DetailsCoinScreen
                navController.navigate("addCoinTransaction?coinId=${coin.coingeckoId}")
            },
            onPortfolioItemClicked = {
                navController.navigate("portfolioEdit/${it.id}")
            }
        )
    }
}