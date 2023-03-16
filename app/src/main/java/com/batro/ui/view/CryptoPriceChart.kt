package com.batro.ui.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.batro.data.model.Coin
import com.batro.ui.model.PriceSnapshot
import com.batro.ui.theme.CryptoAppTheme
import com.batro.ui.theme.primaryColor

val cryptoChartGradient = Brush.verticalGradient(
    listOf(
        primaryColor.copy(alpha = .18f),
        Color.Transparent
    )
)

/**
 * A composable function that displays a line chart of cryptocurrency prices using the provided
 * list of [PriceSnapshot] objects.
 *
 * @param prices A list of [PriceSnapshot] objects representing the historical prices of the
 * cryptocurrency.
 */

@Composable
fun CryptoPriceChart(prices: List<PriceSnapshot>) {
    val path = remember { Path() }
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .padding(vertical = 16.dp)
    ) {
        if (prices.size < 2) {
            return@Canvas
        }
        val maxPrice = prices.maxOf { it.price }
        val minPrice = prices.minOf { it.price }
        val priceRange = maxPrice - minPrice
        val xStep = size.width / (prices.size - 1)
        val yStep = size.height / priceRange

        path.reset()
        prices.forEachIndexed { index, snapshot ->
            val x = index * xStep
            val y = size.height - (snapshot.price - minPrice) * yStep
            if (index == 0) {
                path.moveTo(x, y)
            } else {
                path.lineTo(x, y)
            }
        }
        drawPath(path, color = primaryColor, style = Stroke(width = 1.dp.toPx()))
        path.lineTo(size.width, size.height)
        path.lineTo(0f, size.height)
        drawPath(
            path = path,
            brush = cryptoChartGradient,
            style = Fill
        )
    }
}


@Preview(showBackground = true)
@Composable
fun CryptoPriceChartPreview() = CryptoAppTheme {
    val prices: List<PriceSnapshot> = listOf(
        PriceSnapshot(1,315.0f),
        PriceSnapshot(2,316.7f),
        PriceSnapshot(3,324.76f),
        PriceSnapshot(4,336.46f),
        PriceSnapshot(5,304.54f),
        PriceSnapshot(6,312.4f),
        PriceSnapshot(7,307.12f),
        PriceSnapshot(8,301.99f)
    )
    CryptoPriceChart(prices)
}