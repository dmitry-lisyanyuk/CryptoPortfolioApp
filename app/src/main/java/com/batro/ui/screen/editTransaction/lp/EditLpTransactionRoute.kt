package com.batro.ui.screen.editTransaction.lp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel

@Composable
fun EditLpTransactionRoute(
    txId: Int,
    navController: NavHostController = rememberNavController(),
    viewModel: EditLpTransactionViewModel = mavericksViewModel(argsFactory = {
        EditLpTransactionArgument(txId)
    })
) {

    val state = viewModel.collectAsState()

    EditLpTransactionScreen(
        state.value,
        onEditTransaction = {
            viewModel.updatePortfolioInfo(it)
            navController.navigateUp()
        },
        onDeleteTransaction = {
            viewModel.deleteLpTransaction()
            navController.navigateUp()
        }
    )
}