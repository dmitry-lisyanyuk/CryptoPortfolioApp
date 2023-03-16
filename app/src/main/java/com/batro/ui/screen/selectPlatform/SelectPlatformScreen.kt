@file:OptIn(ExperimentalFoundationApi::class)

package com.batro.ui.screen.selectPlatform

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyColumn
import com.airbnb.mvrx.Success

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.airbnb.mvrx.Loading
import com.batro.R
import com.batro.ui.theme.CryptoAppTheme
import com.batro.ui.view.*
import com.batro.data.model.Platform


@Composable
fun SelectPlatformScreen(
    state: SelectPlatformState,
    onPlatformSelected: (Platform) -> Unit
) {
    BaseAppScaffold(
        title = stringResource(R.string.select_select_platform_title)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(top = 16.dp)
        ) {
            when (state.request) {
                is Success -> {
                    val platforms = state.request.invoke()
                    LazyColumn(
                        Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {
                        itemsIndexed(
                            items = platforms,
                            key = { _, item -> item.id }) { index, platform ->
                            SelectableItem(
                                "",
                                icon = rememberAsyncImagePainter(
                                    model = platform.icon,
                                    placeholder = painterResource(R.drawable.ic_coin_holder)
                                ),
                                title = platform.name,
                                modifier = Modifier.animateItemPlacement(),
                                onClick = {
                                    onPlatformSelected.invoke(platform)
                                },
                                showChevron = false
                            )
                            if (index < platforms.lastIndex) {
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
fun SelectPlatformScreenPreview() = CryptoAppTheme {

}