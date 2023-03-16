package com.batro.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.batro.R
import com.batro.ui.theme.CryptoAppTheme
import com.batro.ui.theme.onColor40
import com.batro.ui.theme.onColor80

@Composable
fun OptionItem(
    modifier: Modifier = Modifier,
    iconPainter: Painter,
    title: String,
    subTitle: String,
    onClick: () -> Unit
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(72.dp)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = iconPainter,
            contentDescription = null,
            modifier = Modifier.size(40.dp)
        )

        Column(
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f)
        ) {
            Text(text = title, color = onColor80(), fontSize = 16.sp)
            Text(text = subTitle, color = onColor40(), fontSize = 12.sp)
        }
    }

}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CryptoAppTheme {
        OptionItem(
            iconPainter = painterResource(R.drawable.ic_crypto_currency),
            title = "Crypto currency",
            subTitle = "BTC, ETH, USDT ect."
        ) {

        }
    }
}