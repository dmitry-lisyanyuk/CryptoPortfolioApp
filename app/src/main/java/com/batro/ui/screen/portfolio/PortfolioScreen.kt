@file:OptIn(ExperimentalFoundationApi::class)

package com.batro.ui.screen.portfolio

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.Success
import com.batro.R
import com.batro.ui.theme.*
import com.batro.ui.view.*
import com.batro.ui.model.PortfolioCoinItem
import com.batro.ui.model.PortfolioLPItem
import kotlinx.coroutines.delay

@Composable
fun PortfolioScreen(
    portfolioState: PortfolioState,
    onPortfolioItemClicked: (PortfolioCoinItem) -> Unit,
    onPortfolioLPItemClicked: (PortfolioLPItem) -> Unit,
    onAddNewItemClicked: () -> Unit,
) {
    val items = remember(portfolioState.portfolioItemList) {
        portfolioState.portfolioItemList.invoke() ?: listOf()
    }
    BaseAppScaffold(
        title = stringResource(R.string.main_portfolio_title),
        showBackButton = false,
        contentToolbar = {
            if (portfolioState.totalProfit != null) {
                Spacer(
                    Modifier
                        .width(8.dp)
                        .weight(1.0f)
                )
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(text = stringResource(id = R.string.base_total), fontSize = 14.sp)
                    Text(
                        text = portfolioState.totalProfit.totalPrice,
                        color = onColor40(),
                        fontSize = 14.sp
                    )
                }
                Spacer(Modifier.width(8.dp))
                Profit(
                    modifier = Modifier.width(84.dp),
                    profitText = portfolioState.totalProfit.profit,
                    profitPercentText = portfolioState.totalProfit.profitPercent,
                    profitType = portfolioState.totalProfit.profitType
                )
            }
        }
    ) {

        when {
            portfolioState.portfolioItemList is Loading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(Modifier.size(48.dp), strokeWidth = 5.dp)
                }
            }
            portfolioState.portfolioItemList is Success
                    && portfolioState.portfolioItemList.invoke().isEmpty() -> {
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_path0),
                        contentDescription = null
                    )
                    Spacer(Modifier.height(20.dp))
                    Text(
                        text = stringResource(R.string.main_portfolio_empty_list),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = onColor80(),
                        fontSize = 16.sp
                    )
                    Spacer(Modifier.height(42.dp))
                    Button(
                        onClick = onAddNewItemClicked, modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Text(stringResource(R.string.main_portfolio_empty_list_add_transaction_button))
                    }
                }
            }
            else -> {
                LazyColumn(
                    Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(vertical = 16.dp)
                ) {
                    portfolioItemList(
                        items,
                        onPortfolioItemClicked = onPortfolioItemClicked,
                        onPortfolioLPItemClicked = onPortfolioLPItemClicked
                    )
                    item(key = -1) {
                        Text(
                            text = stringResource(R.string.main_portfolio_add_transaction_button),
                            modifier = Modifier
                                .animateItemPlacement()
                                .fillMaxWidth()
                                .clickable(onClick = onAddNewItemClicked)
                                .padding(12.dp),
                            color = primaryColor,
                            fontSize = 22.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}


@Preview(showSystemUi = true, device = Devices.PIXEL_4)
@Preview(showSystemUi = true, device = Devices.PIXEL_4, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PortfolioScreenPreview(
    @PreviewParameter(PortfolioScreenStateProvider::class) state: PortfolioState
) = CryptoAppTheme {
    PortfolioScreen(
        state,
        onPortfolioItemClicked = {},
        onPortfolioLPItemClicked = {},
        onAddNewItemClicked = {}
    )
}

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4)
@Composable
fun PortfolioScreenInteractivePreview() {
    CryptoAppTheme {
        val state = remember {
            mutableStateOf(loadingState())
        }
        PortfolioScreen(
            state.value,
            onPortfolioItemClicked = {
                state.value = state.value.copy(
                    portfolioItemList = Success(
                        state.value.portfolioItemList.invoke()!!.minus(it)
                    )
                )
            },
            onPortfolioLPItemClicked = {
                state.value = state.value.copy(
                    portfolioItemList = Success(
                        state.value.portfolioItemList.invoke()!!.minus(it)
                    )
                )
            },
            onAddNewItemClicked = {
                val newItem = PortfolioCoinItemProvider.previewItems.random()
                state.value = state.value.copy(
                    portfolioItemList = Success(
                        state.value.portfolioItemList.invoke()!!
                            .plus(newItem)
                    )
                )
            }
        )
        if (state.value.portfolioItemList is Loading) {
            LaunchedEffect(true) {
                delay(1000)
                state.value = successState()
            }
        }
    }
}

class PortfolioScreenStateProvider : PreviewParameterProvider<PortfolioState> {

    companion object {
        val previewItems = listOf(
            successState(),
            successEmptyState()
        )
    }

    override val values = previewItems.asSequence()
}


fun successState() = PortfolioState(
    portfolioItemList = Success(PortfolioCoinItemProvider.previewItems)
)

fun successEmptyState() = PortfolioState(
    portfolioItemList = Success(listOf())
)

fun loadingState() = PortfolioState(
    portfolioItemList = Loading()
)