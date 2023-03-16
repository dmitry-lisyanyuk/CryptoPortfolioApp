@file:OptIn(ExperimentalFoundationApi::class)

package com.batro.ui.screen.delailsPorfolio.coin

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.batro.R
import com.batro.ui.theme.onColor80
import com.batro.ui.theme.primaryColor
import com.batro.ui.model.PortfolioCoinItem
import com.batro.ui.view.*

@Composable
fun DetailsCoinScreen(
    state: DetailsCoinState,
    onPortfolioItemClicked: (PortfolioCoinItem) -> Unit,
    onAddNewItemClicked: () -> Unit
) {

    val coin = state.coinRequest.invoke() ?: return
    val marketChart = state.marketChartRequest.invoke() ?: return
    state.detailsCoinInfo ?: return

    val items = state.detailsCoinInfo.txList

    LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(top = 0.dp)) {
        item(key = -8) {
            CryptoPriceChart(marketChart)
        }

        item(key = -1) {
            InfoItem(
                titleLeft = stringResource(R.string.base_total_of, coin.name),
                valueLeft = state.detailsCoinInfo.totalAmount,
                titleRight = stringResource(R.string.base_total_worth),
                valueRight = state.detailsCoinInfo.totalWorth
            )
            InfoItem(
                titleLeft = stringResource(R.string.base_avg_buy),
                valueLeft = state.detailsCoinInfo.avgPrice,
                titleRight = stringResource(R.string.base_current_price),
                valueRight = state.detailsCoinInfo.currentPrice
            )
            Divider(Modifier.padding(horizontal = 16.dp))
            Text(
                text = stringResource(R.string.base_transactions),
                modifier = Modifier
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                    .animateItemPlacement(),
                color = onColor80(),
                fontSize = 16.sp
            )
        }
        portfolioItemList(
            items,
            onPortfolioItemClicked = onPortfolioItemClicked,
            onPortfolioLPItemClicked = { }
        )
        item(key = -5) {
            Text(
                text = stringResource(R.string.base_add_transaction),
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