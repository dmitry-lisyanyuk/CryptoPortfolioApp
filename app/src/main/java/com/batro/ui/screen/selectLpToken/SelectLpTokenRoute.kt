package com.batro.ui.screen.selectLpToken

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel

import com.batro.util.extension.setResult

const val LP_TOKEN_EXTRA = "lpToken"

@Composable
fun SelectLpTokenRoute(
    platformId: String,
    navController: NavHostController = rememberNavController(),
    viewModel: SelectLpTokenViewModel = mavericksViewModel(argsFactory = {
        SelectLpTokenArgument(platformId)
    })
) {
    val state = viewModel.collectAsState()

    SelectLpTokenScreen(
        state.value,
        onTokenSelected = { token ->
            navController.setResult(LP_TOKEN_EXTRA, token)
            navController.navigateUp()
        }
    )
}