package com.batro.ui.screen.editTransaction.coin

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel

@Composable
fun EditCoinTransactionRoute(
    txId: Int,
    navController: NavHostController = rememberNavController(),
    viewModel: EditCoinTransactionViewModel = mavericksViewModel(argsFactory = {
        EditCoinTransactionArgument(txId)
    })
) {

    val state = viewModel.collectAsState()

    EditCoinTransactionScreen(
        state.value,
        onEditTransaction = {
            viewModel.updateTransaction(it)
            navController.navigateUp()
        },
        onDeleteTransaction = {
            viewModel.deleteTransaction()
            navController.navigateUp()
        }
    )
}