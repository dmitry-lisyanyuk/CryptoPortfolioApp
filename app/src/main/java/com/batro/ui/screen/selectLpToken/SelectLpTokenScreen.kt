@file:OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)

package com.batro.ui.screen.selectLpToken

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyColumn
import com.airbnb.mvrx.Success

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.mvrx.Loading
import com.batro.R
import com.batro.ui.theme.CryptoAppTheme
import com.batro.ui.view.*
import com.batro.data.model.LPToken


@Composable
fun SelectLpTokenScreen(
    state: SelectLpTokenState,
    onTokenSelected: (LPToken) -> Unit
) {
    BaseAppScaffold(
        title = stringResource(R.string.select_lp_token_title)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(top = 16.dp)
        ) {
            when (state.request) {
                is Success -> {
                    val lpTokenList = state.request.invoke()
                    LazyColumn(
                        Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {
                        itemsIndexed(
                            items = lpTokenList,
                            key = { _, item -> item.contractAddress }) { index, lpToken ->
                            LpTokenItem(
                                lpToken = lpToken,
                                modifier = Modifier.animateItemPlacement(),
                                showChevron = false
                            ) {
                                onTokenSelected.invoke(lpToken)
                            }
                            if (index < lpTokenList.lastIndex) {
                                Divider(
                                    Modifier
                                        .animateItemPlacement()
                                        .padding(horizontal = 16.dp)
                                )
                            }
                        }
                    }
                }
                is Loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(Modifier.size(48.dp), strokeWidth = 5.dp)
                    }
                }
                else -> {

                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SelectLpTokenScreenPreview() = CryptoAppTheme {

}