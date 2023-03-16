package com.batro.ui.screen.editTransaction.lp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.batro.R
import com.batro.ui.screen.addCoinTransaction.amountFilter
import com.batro.ui.theme.CryptoAppTheme
import com.batro.ui.theme.redColor
import com.batro.ui.view.*
import com.batro.data.model.PortfolioLpTransaction
import java.math.MathContext


@Composable
fun EditLpTransactionScreen(
    state: EditLpTransactionState,
    onEditTransaction: (PortfolioLpTransaction) -> Unit,
    onDeleteTransaction: () -> Unit
) {
    BaseAppScaffold(
        title = stringResource(R.string.edit_lp_transaction_title)
    ) {
        val transaction = state.portfolioLpTransaction ?: return@BaseAppScaffold
        Column(
            Modifier
                .fillMaxSize()
                .padding(vertical = 8.dp)
        ) {
            SelectableItem(
                selectText = "",
                icon = state.platform?.let {
                    rememberAsyncImagePainter(
                        model = it.icon,
                        placeholder = painterResource(R.drawable.ic_coin_holder)
                    )
                },
                title = state.platform?.name,
                modifier = Modifier.height(52.dp),
                clickable = false,
                showChevron = false,
                onClick = { }
            )
            Divider(Modifier.padding(horizontal = 16.dp))
            LpTokenItem(
                lpToken = transaction.lpToken,
                modifier = Modifier.height(52.dp),
                clickable = false,
                showChevron = false,
                onClick = { })
            Divider(Modifier.padding(horizontal = 16.dp))
            Spacer(Modifier.height(4.dp))
            var amountLpInput by rememberSaveable {
                mutableStateOf(transaction.lpAmount.toPlainString())
            }
            AppTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                value = amountLpInput,
                onValueChange = {
                    if (amountFilter.isValid(it)) {
                        amountLpInput = it
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                label = { Text(stringResource(R.string.edit_lp_transaction_input_lp_amount)) }
            )
            Spacer(Modifier.height(8.dp))
            var amount1Input by rememberSaveable(transaction.amount1) {
                mutableStateOf(transaction.amount1.toPlainString())
            }
            AppTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                value = amount1Input,
                onValueChange = {
                    if (amountFilter.isValid(it)) {
                        amount1Input = it
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                label = {
                    Text(
                        stringResource(
                            R.string.edit_lp_transaction_input_initial_coin_amount,
                            transaction.lpToken.coin0.symbol.uppercase()
                        )
                    )
                }
            )
            Spacer(Modifier.height(8.dp))
            var amount2Input by rememberSaveable(transaction.amount2) {
                mutableStateOf(transaction.amount2.toPlainString())
            }
            AppTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                value = amount2Input,
                onValueChange = {
                    if (amountFilter.isValid(it)) {
                        amount2Input = it
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                label = {
                    Text(
                        stringResource(
                            R.string.edit_lp_transaction_input_initial_coin_amount,
                            transaction.lpToken.coin1.symbol.uppercase()
                        )
                    )
                }
            )
            Spacer(Modifier.height(32.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(horizontal = 16.dp),
                onClick = {
                    val amount =
                        amountLpInput.toBigDecimalOrNull(MathContext.UNLIMITED) ?: return@Button
                    val amount1 =
                        amount1Input.toBigDecimalOrNull(MathContext.UNLIMITED) ?: return@Button
                    val amount2 =
                        amount2Input.toBigDecimalOrNull(MathContext.UNLIMITED) ?: return@Button
                    onEditTransaction.invoke(
                        transaction.copy(
                            lpAmount = amount,
                            amount1 = amount1,
                            amount2 = amount2
                        )
                    )
                }
            ) {
                Text(text = stringResource(R.string.edit_lp_transaction_save_transaction_button))
            }
            Spacer(Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.edit_lp_transaction_delete_transaction_button),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onDeleteTransaction)
                    .padding(12.dp),
                color = redColor,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun EditCoinTransactionScreenPreview() = CryptoAppTheme {

}