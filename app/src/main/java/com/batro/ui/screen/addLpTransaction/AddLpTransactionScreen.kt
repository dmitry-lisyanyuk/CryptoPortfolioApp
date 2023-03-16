package com.batro.ui.screen.addLpTransaction

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.batro.R
import com.batro.ui.screen.addCoinTransaction.amountFilter
import com.batro.ui.theme.CryptoAppTheme
import com.batro.ui.view.*
import com.batro.data.model.PortfolioLpTransaction
import java.math.MathContext


@Composable
fun AddLpTransactionScreen(
    state: AddLpTransactionState,
    onChangePlatform: () -> Unit,
    onChangeLp: () -> Unit,
    onAddTransaction: (PortfolioLpTransaction) -> Unit
) {
    BaseAppScaffold(
        title = stringResource(R.string.add_lp_transaction_title)
    ) {
        Column(
            Modifier
                .fillMaxSize()
        ) {
            Spacer(Modifier.height(8.dp))
            SelectableItem(
                selectText = stringResource(R.string.add_lp_transaction_select_platform_holder),
                icon = state.platform?.let {
                    rememberAsyncImagePainter(
                        model = it.icon,
                        placeholder = painterResource(R.drawable.ic_coin_holder)
                    )
                },
                title = state.platform?.name,
                modifier = Modifier.height(52.dp),
                onClick = onChangePlatform
            )
            Divider(Modifier.padding(horizontal = 16.dp))
            Spacer(Modifier.height(4.dp))
            if (state.platform != null) {
                LpTokenItem(
                    lpToken = state.lpToken,
                    onClick = onChangeLp,
                    modifier = Modifier.height(52.dp)
                )
                Divider(Modifier.padding(horizontal = 16.dp))
                Spacer(Modifier.height(4.dp))
            }

            if (state.platform != null && state.lpToken != null) {
                var amountLpInput by rememberSaveable { mutableStateOf("") }
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
                    label = { Text(stringResource(R.string.add_lp_transaction_input_lp_amount)) }
                )
                Spacer(Modifier.height(8.dp))
                var amount0CoinInput by rememberSaveable(state.lpToken.coin0) { mutableStateOf("") }
                AppTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    value = amount0CoinInput,
                    onValueChange = {
                        if (amountFilter.isValid(it)) {
                            amount0CoinInput = it
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    label = {
                        Text(
                            stringResource(
                                R.string.add_lp_transaction_input_initial_coin_amount,
                                state.lpToken.coin0.symbol.uppercase()
                            )
                        )
                    }
                )
                Spacer(Modifier.height(8.dp))

                var amount1CoinInput by rememberSaveable(state.lpToken.coin1) { mutableStateOf("") }
                AppTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    value = amount1CoinInput,
                    onValueChange = {
                        if (amountFilter.isValid(it)) {
                            amount1CoinInput = it
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    label = {
                        Text(
                            stringResource(
                                R.string.add_lp_transaction_input_initial_coin_amount,
                                state.lpToken.coin1.symbol.uppercase()
                            )
                        )
                    }
                )
                Spacer(Modifier.height(24.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .padding(horizontal = 16.dp),
                    onClick = {
                        val amountLp = amountLpInput.toBigDecimalOrNull(MathContext.UNLIMITED)
                            ?: return@Button
                        val amount0 = amount0CoinInput.toBigDecimalOrNull(MathContext.UNLIMITED)
                            ?: return@Button
                        val amount1 = amount1CoinInput.toBigDecimalOrNull(MathContext.UNLIMITED)
                            ?: return@Button
                        onAddTransaction.invoke(
                            PortfolioLpTransaction(
                                state.lpToken,
                                amountLp,
                                null,
                                amount0,
                                amount1,
                                null,
                                null,
                                null
                            )
                        )
                    }
                ) {
                    Text(text = stringResource(R.string.add_lp_transaction_create_transaction_button))
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AddLpTransactionScreenPreview() = CryptoAppTheme {
    AddLpTransactionScreen(
        AddLpTransactionState(),
        onChangeLp = {

        },
        onChangePlatform = {

        },
        onAddTransaction = {

        }
    )
}