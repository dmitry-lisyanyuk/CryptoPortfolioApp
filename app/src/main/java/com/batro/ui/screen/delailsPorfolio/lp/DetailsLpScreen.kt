@file:OptIn(ExperimentalFoundationApi::class)

package com.batro.ui.screen.delailsPorfolio.lp

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
import com.batro.ui.view.InfoItem
import com.batro.ui.view.portfolioItemList
import com.batro.ui.model.PortfolioLPItem

@Composable
fun DetailsLpScreen(
    state: DetailsLpState,
    onPortfolioItemClicked: (PortfolioLPItem) -> Unit,
    onAddNewItemClicked: () -> Unit
) {

    val lpToken = state.lpTokenRequest.invoke() ?: return

    val items = state.detailsLpInfo?.txList ?: return

    LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(top = 16.dp)) {

        item(key = -1) {
            InfoItem(
                titleLeft = stringResource(R.string.base_total_of, lpToken.toString()),
                valueLeft = state.detailsLpInfo.totalLpAmount,
                titleRight = stringResource(R.string.base_total_worth),
                valueRight = state.detailsLpInfo.totalWorth
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
            onPortfolioItemClicked = { },
            onPortfolioLPItemClicked = onPortfolioItemClicked
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