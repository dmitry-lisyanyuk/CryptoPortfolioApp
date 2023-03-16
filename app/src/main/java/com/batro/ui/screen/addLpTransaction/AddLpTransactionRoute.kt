package com.batro.ui.screen.addLpTransaction

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel
import com.batro.ui.screen.selectLpToken.LP_TOKEN_EXTRA
import com.batro.ui.screen.selectPlatform.PLATFORM_EXTRA
import com.batro.util.extension.GetOnceResult


@Composable
fun AddLpTransactionRoute(
    lpAddress: String?,
    navController: NavHostController = rememberNavController(),
    viewModel: AddLpTransactionViewModel = mavericksViewModel(argsFactory = {
        AddTransactionLpAddressArgument(lpAddress)
    })
) {
    navController.GetOnceResult(LP_TOKEN_EXTRA, viewModel::setLPToken)
    navController.GetOnceResult(PLATFORM_EXTRA, viewModel::setPlatform)
    val state = viewModel.collectAsState()
    AddLpTransactionScreen(
        state.value,
        onChangePlatform = {
            navController.navigate("selectPlatform")
        },
        onChangeLp = {
            navController.navigate("selectLpToken/${state.value.platform?.id ?: return@AddLpTransactionScreen}")
        },
        onAddTransaction = {
            viewModel.addPortfolioInfo(it)
            navController.navigateUp()
        }
    )
}