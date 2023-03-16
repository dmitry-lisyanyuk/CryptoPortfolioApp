@file:OptIn(ExperimentalFoundationApi::class)

package com.batro.ui.screen.selectCoin

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyColumn
import com.airbnb.mvrx.Success

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.mvrx.Loading
import com.batro.R
import com.batro.ui.theme.CryptoAppTheme
import com.batro.ui.view.*
import com.batro.data.model.Coin


@Composable
fun SelectCoinScreen(
    state: SelectCoinState,
    onCoinSelected: (Coin) -> Unit,
    onSearch: (String) -> Unit,
    onScrolledLast: () -> Unit
) {
    BaseAppScaffold(
        title = stringResource(R.string.select_coin_title)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(top = 16.dp)
        ) {

            var searchInput by rememberSaveable {
                mutableStateOf(state.query ?: "")
            }

            SearchField(
                value = searchInput,
                onValueChange = {
                    onSearch.invoke(it)
                    searchInput = it
                }
            )
            Spacer(modifier = Modifier.height(8.dp))

            val listState = rememberSaveable(
                state.loadedQuery,
                saver = LazyListState.Saver
            ) {
                LazyListState()
            }


            val coinList = state.request.invoke()
            if (coinList != null) {
                LazyColumn(
                    Modifier.fillMaxSize(),
                    state = listState,
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    itemsIndexed(
                        items = coinList,
                        key = { _, item -> item.coingeckoId }) { index, coin ->
                        CoinItem(coin, Modifier.animateItemPlacement(), showChevron = false) {
                            onCoinSelected.invoke(coin)
                        }
                        if (index < coinList.lastIndex) {
                            Divider(
                                Modifier
                                    .animateItemPlacement()
                                    .padding(horizontal = 16.dp)
                            )
                        }
                    }
                    if (state.request is Loading) {
                        item(-1) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp)
                                    .animateItemPlacement(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(Modifier.size(36.dp), strokeWidth = 3.dp)
                            }
                        }
                    }
                }
            } else if (state.request is Loading) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(Modifier.size(48.dp), strokeWidth = 5.dp)
                }
            }

            val endOfListReached by remember(state.loadedQuery) {
                derivedStateOf {
                    if (listState.layoutInfo.totalItemsCount == 0) return@derivedStateOf false
                    (listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0) >=
                            listState.layoutInfo.totalItemsCount - 5
                }
            }
            LaunchedEffect(endOfListReached) {
                if (endOfListReached) {
                    onScrolledLast.invoke()
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SelectCoinScreenPreview() = CryptoAppTheme {
    SelectCoinScreen(
        SelectCoinState(request = Success(CoinItemProvider.lists)),
        onCoinSelected = {

        },
        onSearch = {

        },
        onScrolledLast = {

        }
    )
}