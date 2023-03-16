package com.batro.ui.screen.selectPlatform

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel

import com.batro.util.extension.setResult

const val PLATFORM_EXTRA = "platform"

@Composable
fun SelectPlatformRoute(
    navController: NavHostController = rememberNavController(),
    viewModel: SelectPlatformViewModel = mavericksViewModel()
) {
    val state = viewModel.collectAsState()

    SelectPlatformScreen(
        state.value,
        onPlatformSelected = { platform ->
            navController.setResult(PLATFORM_EXTRA, platform)
            navController.navigateUp()
        }
    )
}