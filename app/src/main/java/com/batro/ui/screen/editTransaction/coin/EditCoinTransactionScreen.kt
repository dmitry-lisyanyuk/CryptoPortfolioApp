package com.batro.ui.screen.editTransaction.coin

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.mvrx.Success
import com.batro.R
import com.batro.ui.screen.addCoinTransaction.amountFilter
import com.batro.ui.theme.CryptoAppTheme
import com.batro.ui.theme.redColor
import com.batro.ui.view.AppTextField
import com.batro.ui.view.BaseAppScaffold
import com.batro.ui.view.CoinItem
import com.batro.ui.view.CoinItemProvider
import com.batro.data.model.PortfolioCoinTransaction
import java.math.BigDecimal
import java.math.MathContext


@Composable
fun EditCoinTransactionScreen(
    state: EditCoinTransactionState,
    onEditTransaction: (PortfolioCoinTransaction) -> Unit,
    onDeleteTransaction: () -> Unit
) {
    BaseAppScaffold(
        title = stringResource(R.string.edit_coin_transaction_title)
    ) {
        val transaction = state.portfolioCoinTransaction ?: return@BaseAppScaffold
        Column(
            Modifier
                .fillMaxSize()
        ) {
            Spacer(Modifier.height(8.dp))
            CoinItem(
                coin = transaction.coin,
                modifier = Modifier.height(52.dp),
                clickable = false,
                showChevron = false,
                onClick = { })
            Divider(Modifier.padding(horizontal = 16.dp))
            Spacer(Modifier.height(4.dp))
            var amountInput by rememberSaveable {
                mutableStateOf(transaction.amount.toPlainString())
            }
            AppTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                value = amountInput,
                onValueChange = {
                    if (amountFilter.isValid(it)) {
                        amountInput = it
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                label = { Text(stringResource(R.string.edit_coin_transaction_input_amount)) }
            )
            Spacer(Modifier.height(8.dp))
            var priceInput by rememberSaveable {
                mutableStateOf(transaction.price.toPlainString())
            }
            AppTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                value = priceInput,
                onValueChange = {
                    if (amountFilter.isValid(it)) {
                        priceInput = it
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                label = { Text(stringResource(R.string.edit_coin_transaction_input_price)) }
            )
            Spacer(Modifier.height(8.dp))
            var noteInput by rememberSaveable {
                mutableStateOf(transaction.note ?: "")
            }
            AppTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                value = noteInput,
                onValueChange = {
                    noteInput = it
                },
                label = { Text(stringResource(R.string.edit_coin_transaction_input_note)) }
            )
            Spacer(Modifier.height(32.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(horizontal = 16.dp),
                onClick = {
                    val amount = amountInput.toBigDecimalOrNull(MathContext.UNLIMITED)
                        ?: return@Button
                    val price = priceInput.toBigDecimalOrNull(MathContext.UNLIMITED)
                        ?: return@Button
                    val note = noteInput.let { if (it.trim().isEmpty()) null else it }
                    onEditTransaction.invoke(
                        transaction.copy(
                            amount = amount,
                            price = price,
                            note = note
                        )
                    )
                }
            ) {
                Text(text = stringResource(R.string.edit_coin_transaction_save_transaction_button))
            }
            Spacer(Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.edit_coin_transaction_delete_transaction_button),
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
    EditCoinTransactionScreen(
        EditCoinTransactionState(
            portfolioCoinItemId = 1,
            portfolioCoinTransaction = PortfolioCoinTransaction(
                coin = CoinItemProvider.btc,
                amount = BigDecimal.valueOf(0.005),
                price = BigDecimal.valueOf(35600),
                note = null,
                1
            )
        ),
        onEditTransaction = {

        },
        onDeleteTransaction = {

        }
    )
}