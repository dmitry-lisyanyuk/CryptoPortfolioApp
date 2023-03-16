@file:OptIn(ExperimentalMaterialApi::class)

package com.batro.ui.screen.portfolio

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.batro.R
import com.batro.ui.theme.CryptoAppTheme
import com.batro.ui.view.DefaultBottomSheetLayout
import com.batro.ui.view.OptionItem
import com.batro.util.extension.rememberDefaultModalBottomSheetState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun PortfolioAddTransactionDialog(
    modalSheetState: ModalBottomSheetState = rememberDefaultModalBottomSheetState(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    onAddCoinTransaction: () -> Unit,
    onAddLPTransaction: () -> Unit,
    content: @Composable () -> Unit
) {
    BackHandler(modalSheetState.isVisible) {
        coroutineScope.launch { modalSheetState.hide() }
    }
    DefaultBottomSheetLayout(
        title = stringResource(R.string.main_portfolio_add_transaction_dialog_title),
        modalSheetState,
        sheetContent = {
            Column {
                OptionItem(
                    iconPainter = painterResource(R.drawable.ic_crypto_currency),
                    title = stringResource(R.string.main_portfolio_add_transaction_dialog_coin_title),
                    subTitle = stringResource(R.string.main_portfolio_add_transaction_dialog_coin_subtitle)
                ) {
                    coroutineScope.launch {
                        modalSheetState.hide()
                        onAddCoinTransaction.invoke()
                    }
                }
                Divider(Modifier.padding(horizontal = 16.dp))
                OptionItem(
                    iconPainter = painterResource(R.drawable.ic_liquidity_pool_token),
                    title = stringResource(R.string.main_portfolio_add_transaction_dialog_lp_title),
                    subTitle = stringResource(R.string.main_portfolio_add_transaction_dialog_lp_subtitle)
                ) {
                    coroutineScope.launch {
                        modalSheetState.hide()
                        onAddLPTransaction.invoke()
                    }
                }
            }
        },
        content = content
    )
}


@Preview(showBackground = true)
@Composable
fun PortfolioAddTransactionDialogPreview() = CryptoAppTheme {
    PortfolioAddTransactionDialog(
        modalSheetState = rememberDefaultModalBottomSheetState(ModalBottomSheetValue.Expanded),
        onAddLPTransaction = {

        },
        onAddCoinTransaction = {

        }
    ) {}
}