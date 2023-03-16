@file:OptIn(ExperimentalMaterialApi::class)

package com.batro.ui.screen.portfolio

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksActivityViewModel
import com.batro.util.extension.rememberDefaultModalBottomSheetState
import kotlinx.coroutines.launch

@Composable
fun PortfolioRoute(
    navHostController: NavHostController = rememberNavController(),
    viewModel: PortfolioViewModel = mavericksActivityViewModel()
) {
    val coroutineScope = rememberCoroutineScope()

    val modalSheetState = rememberDefaultModalBottomSheetState()

    val state = viewModel.collectAsState()
    PortfolioScreen(
        state.value,
        onPortfolioItemClicked = { item ->
            navHostController.navigate("portfolioDetails/${item.currency.coingeckoId}")
        },
        onPortfolioLPItemClicked = { item ->
            navHostController.navigate("portfolioLpDetails/${item.lpToken.contractAddress}")
        },
        onAddNewItemClicked = {
            coroutineScope.launch {
                modalSheetState.show()
            }
        }
    )
    PortfolioAddTransactionDialog(
        modalSheetState,
        coroutineScope,
        onAddCoinTransaction = {
            navHostController.navigate("addCoinTransaction")
        },
        onAddLPTransaction = {
            navHostController.navigate("addLpTransaction")
        },
        content = {}
    )
}


