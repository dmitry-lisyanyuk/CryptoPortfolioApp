package com.batro.ui.screen.addCoinTransaction

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.batro.R
import com.batro.ui.theme.CryptoAppTheme
import com.batro.ui.view.AppTextField
import com.batro.ui.view.BaseAppScaffold
import com.batro.ui.view.CoinItem
import com.batro.ui.view.CoinItemProvider
import com.batro.data.model.PortfolioCoinTransaction
import com.batro.util.DecimalDigitsInputFilter
import java.math.MathContext


val amountFilter = DecimalDigitsInputFilter(9, 18)

@Composable
fun AddCoinTransactionScreen(
    state: AddCoinTransactionState,
    onChangeCoin: () -> Unit,
    onAddTransaction: (PortfolioCoinTransaction) -> Unit
) {
    BaseAppScaffold(
        title = stringResource(R.string.add_coin_transaction_title)
    ) {
        Column(
            Modifier.fillMaxSize()
        ) {
            Spacer(Modifier.height(8.dp))
            CoinItem(coin = state.coin, modifier = Modifier.height(52.dp), onClick = onChangeCoin)
            Divider(Modifier.padding(horizontal = 16.dp))
            Spacer(Modifier.height(4.dp))
            var amountInput by rememberSaveable { mutableStateOf("") }
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
                label = { Text(stringResource(R.string.add_coin_transaction_input_amount)) }
            )
            Spacer(Modifier.height(8.dp))
            var priceInput by rememberSaveable(state.coin) { mutableStateOf("") }
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
                label = { Text(stringResource(R.string.add_coin_transaction_input_price)) }
            )
            Spacer(Modifier.height(8.dp))
            var noteInput by rememberSaveable {
                mutableStateOf( "")
            }
            AppTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                value = noteInput,
                onValueChange = {
                    noteInput = it
                },
                label = { Text(stringResource(R.string.add_coin_transaction_input_note)) }
            )
            Spacer(Modifier.height(56.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(horizontal = 16.dp),
                onClick = {
                    val coin = state.coin ?: return@Button
                    val amount = amountInput.toBigDecimalOrNull(MathContext.UNLIMITED)
                        ?: return@Button
                    val price = priceInput.toBigDecimalOrNull(MathContext.UNLIMITED)
                        ?: return@Button
                    val note = noteInput.let { if (it.trim().isEmpty()) null else it }
                    onAddTransaction.invoke(PortfolioCoinTransaction(coin, amount, price, note))
                }
            ) {
                Text(text = stringResource(R.string.add_coin_transaction_create_transaction_button))
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AddTransactionScreenPreview() = CryptoAppTheme {
    AddCoinTransactionScreen(
        AddCoinTransactionState(coin = CoinItemProvider.btc),
        onChangeCoin = {

        },
        onAddTransaction = {

        }
    )
}