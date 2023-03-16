package com.batro.ui.screen.delailsPorfolio.lp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel
import com.batro.ui.view.BaseAppScaffold

@Composable
fun DetailsLpRoute(
    lpAddress: String,
    navController: NavHostController = rememberNavController(),
    viewModel: DetailsLpViewModel = mavericksViewModel(argsFactory = {
        DetailsLpAddressArgument(lpAddress)
    })
) {

    val state = viewModel.collectAsState()

    if (state.value.detailsLpInfo?.txList?.isEmpty() == true) {
        navController.navigateUp()
        return
    }
    val title = remember(state.value.lpTokenRequest) {
        state.value.lpTokenRequest.invoke()?.toString() ?: ""
    }
    BaseAppScaffold(
        title = title
    ) {
        DetailsLpScreen(
            state.value,
            onAddNewItemClicked = {
                val lpToken = state.value.lpTokenRequest.invoke() ?: return@DetailsLpScreen
                navController.navigate("addLpTransaction?lpAddress=${lpToken.contractAddress}")
            },
            onPortfolioItemClicked = {
                navController.navigate("portfolioLpEdit/${it.id}")
            }
        )
    }
}