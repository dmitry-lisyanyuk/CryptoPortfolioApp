@file:OptIn(ExperimentalFoundationApi::class)

package com.batro.ui.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.batro.ui.theme.*

import com.batro.data.model.Coin
import com.batro.data.model.LPToken
import com.batro.ui.model.ProfitType
import com.batro.ui.model.PortfolioCoinItem
import com.batro.ui.model.PortfolioItem
import com.batro.ui.model.PortfolioLPItem
import com.batro.util.extension.convertUnitOfDecimal
import com.batro.util.extension.fromWei
import com.batro.util.extension.longAmountToString
import com.batro.R
import org.web3j.utils.Convert
import java.math.BigDecimal
import java.math.BigInteger
import java.math.MathContext
import java.math.RoundingMode
import kotlin.math.absoluteValue


@Composable
fun Portfolio(
    item: PortfolioCoinItem,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .height(56.dp)
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.background)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        val title = remember(item.currency, item.amount) {
            item.currency.symbol.uppercase() + " " + item.amount.fromWei()
        }
        val priceTotal = remember(item.totalPriceUSD) {
            "$${item.totalPriceUSD.longAmountToString()}"
        }
        val price = remember(item.price) {
            "$${item.price.toPlainString()}"
        }
        val profit = remember(item.totalPriceUSD, item.initialPriceUSD) {
            item.totalPriceUSD - item.initialPriceUSD
        }
        val profitText = remember(profit) {
            "${if (profit < 0) "-" else ""}$${profit.absoluteValue.longAmountToString()}"
        }
        val per = remember(profit, item.initialPriceUSD) {
            if (item.initialPriceUSD == 0L) {
                "100%"
            } else {
                profit.toBigDecimal()
                    .divide(item.initialPriceUSD.toBigDecimal(), MathContext.DECIMAL128)
                    .multiply(BigDecimal(100)).setScale(1, RoundingMode.DOWN)
                    .toPlainString() + "%"
            }
        }


        Image(
            painter = rememberAsyncImagePainter(
                model = item.currency.icon,
                placeholder = painterResource(R.drawable.ic_coin_holder)
            ),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(1f)
        ) {
            Text(text = title, color = onColor80(), fontSize = 12.sp)
            Text(text = priceTotal, color = onColor40(), fontSize = 10.sp)
            if (item.note != null){
                Text(text = item.note, color = onColor40(), fontSize = 10.sp)
            }
        }
        Text(text = price, color = onColor40(), fontSize = 12.sp)
        Spacer(modifier = Modifier.width(8.dp))

        val profitType = remember(profit) {
            when {
                profit == 0L -> ProfitType.INDIFFERENT
                profit > 0L -> ProfitType.POSITIVE
                else -> ProfitType.NEGATIVE
            }
        }
        Profit(
            profitText = profitText,
            profitPercentText = per,
            profitType = profitType
        )
    }
}

@Composable
fun PortfolioLP(
    item: PortfolioLPItem,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .height(56.dp)
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.background)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        val title = remember(item.lpToken, item.lpAmount) {
            "${item.lpToken.coin0.symbol.uppercase()}-${item.lpToken.coin1.symbol.uppercase()} ${item.lpAmount.fromWei()}"
        }
        val priceTotal = remember(item.lpPriceUSD) {
            "$${item.lpPriceUSD.longAmountToString()}"
        }
        val profit = remember(item.lpPriceUSD, item.initialPriceUSD) {
            item.lpPriceUSD - item.initialPriceUSD
        }
        val profitText = remember(profit) {
            "${if (profit < 0) "-" else ""}$${profit.absoluteValue.longAmountToString()}"
        }
        val per = remember(profit, item.initialPriceUSD) {
            if (item.initialPriceUSD == 0L) {
                "100%"
            } else {
                profit.toBigDecimal()
                    .divide(item.initialPriceUSD.toBigDecimal(), MathContext.DECIMAL128)
                    .multiply(BigDecimal(100)).setScale(1, RoundingMode.DOWN)
                    .toPlainString() + "%"
            }
        }


        Box(modifier = Modifier.size(30.dp)) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = item.lpToken.coin1.icon,
                    placeholder = painterResource(R.drawable.ic_coin_holder)
                ),
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
            Image(
                painter = rememberAsyncImagePainter(
                    model = item.lpToken.coin0.icon,
                    placeholder = painterResource(R.drawable.ic_coin_holder)
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(22.dp)
                    .align(Alignment.BottomEnd)
            )
        }
        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(1f)
        ) {
            Text(text = title, color = onColor80(), fontSize = 12.sp)
            Text(text = priceTotal, color = onColor40(), fontSize = 10.sp)
        }

        val lpReward = remember(item.lpProfitUSD) {
            if (item.lpProfitUSD < 0) {
                "Im. Loss -$${item.lpProfitUSD.absoluteValue.longAmountToString()}"
            } else {
                "LP reward $${item.lpProfitUSD.longAmountToString()}"
            }
        }
        val lpCoinPrice1 = remember(item.lpProfitUSD, item.token1Amount) {
            item.token1Amount.fromWei(convertUnitOfDecimal(item.lpToken.currency0Decimals)) + " " + item.lpToken.coin0.symbol.uppercase()
        }
        val lpCoinPrice2 = remember(item.lpToken, item.token2Amount) {
            item.token2Amount.fromWei(convertUnitOfDecimal(item.lpToken.currency1Decimals)) + " " + item.lpToken.coin1.symbol.uppercase()
        }
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.End
        ) {
            Text(text = lpCoinPrice1, color = onColor40(), fontSize = 10.sp)
            Text(text = lpCoinPrice2, color = onColor40(), fontSize = 10.sp)
            Text(
                text = lpReward,
                color = if (item.lpProfitUSD < 0) redColor else greenColor,
                fontSize = 10.sp
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        val profitType = remember(profit) {
            when {
                profit == 0L -> ProfitType.INDIFFERENT
                profit > 0L -> ProfitType.POSITIVE
                else -> ProfitType.NEGATIVE
            }
        }
        Profit(
            profitText = profitText,
            profitPercentText = per,
            profitType = profitType
        )
    }
}

fun LazyListScope.portfolioItemList(
    items: List<PortfolioItem>,
    onPortfolioItemClicked: (PortfolioCoinItem) -> Unit,
    onPortfolioLPItemClicked: (PortfolioLPItem) -> Unit
) {
    itemsIndexed(
        items = items,
        key = { _, item -> item.id },
        contentType = { _, item ->
            when (item) {
                is PortfolioCoinItem -> 1
                is PortfolioLPItem -> 2
            }
        }
    ) { index, portfolioItem ->
        when (portfolioItem) {
            is PortfolioCoinItem -> Portfolio(portfolioItem, Modifier.animateItemPlacement()) {
                onPortfolioItemClicked.invoke(portfolioItem)
            }
            is PortfolioLPItem -> PortfolioLP(portfolioItem, Modifier.animateItemPlacement()) {
                onPortfolioLPItemClicked.invoke(portfolioItem)
            }
        }
        if (index < items.lastIndex) {
            Divider(
                Modifier
                    .animateItemPlacement()
                    .padding(horizontal = 16.dp)
            )
        }
    }
}


@Composable
fun Profit(
    profitText: String,
    profitPercentText: String,
    profitType: ProfitType,
    modifier: Modifier = Modifier
) {
    val profitColor = when (profitType) {
        ProfitType.POSITIVE -> greenColor
        ProfitType.NEGATIVE -> redColor
        ProfitType.INDIFFERENT -> grayColor
    }
    val profitColorBG = when (profitType) {
        ProfitType.POSITIVE -> greenColor10
        ProfitType.NEGATIVE -> redColor10
        ProfitType.INDIFFERENT -> grayColor10
    }
    Column(
        modifier
            .width(70.dp)
            .height(42.dp)
            .background(profitColorBG, RoundedCornerShape(5.dp))
            .padding(end = 6.dp),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = profitText,
            color = profitColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1
        )
        Text(
            text = profitPercentText,
            color = profitColor,
            fontSize = 10.sp,
            maxLines = 1
        )
    }
}


class PortfolioCoinItemProvider : PreviewParameterProvider<PortfolioItem> {

    companion object {


        val btcCoin = Coin("btc", "BTC", "BTC", "", 3294)
        val usdtCoin = Coin("usdt", "Tether", "USDT", "", 3291)
        val btcUsdtLp = LPToken(
            contractAddress = "",
            coin0 = btcCoin,
            coin1 = usdtCoin,
            pid = 1,
            currency0Decimals = 18,
            currency1Decimals = 18,
            lpDecimals = 18,
            serviceId = "test",
            chainId = "ter",
            poolExtra = null
        )


        val previewItems = listOf<PortfolioItem>(
            PortfolioCoinItem(
                currency = btcCoin,
                amount = BigInteger.valueOf(5346457235234534534),
                totalPriceUSD = 20120000,
                initialPriceUSD = 20000000,
                price = BigDecimal.valueOf(31245.54),
                null,
                123
            ),
            PortfolioCoinItem(
                currency = btcCoin,
                amount = BigInteger.valueOf(5346457235234534534),
                totalPriceUSD = 20000000,
                initialPriceUSD = 20120000,
                price = BigDecimal.valueOf(31245.54),
                null,
                124
            ),
            PortfolioCoinItem(
                currency = btcCoin,
                amount = BigInteger.valueOf(5346457235234534534),
                totalPriceUSD = 20000000,
                initialPriceUSD = 20000000,
                price = BigDecimal.valueOf(31245.54),
                null,
                125
            ),
            PortfolioLPItem(
                lpToken = btcUsdtLp,
                initialPriceUSD = 20000000,
                lpAmount = BigInteger.valueOf(10000000000000),
                token1Amount = BigInteger.valueOf(5346235234534534),
                token2Amount = BigInteger.valueOf(5343457235234534534),
                lpPriceUSD = 343289L,
                lpProfitUSD = 2434534L,
                id = 1
            )
        )
    }

    override val values = previewItems.asSequence()
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview(
    @PreviewParameter(PortfolioCoinItemProvider::class) portfolioCoinItem: PortfolioItem
) {
    CryptoAppTheme {
        when (portfolioCoinItem) {
            is PortfolioCoinItem -> Portfolio(portfolioCoinItem) {

            }
            is PortfolioLPItem -> PortfolioLP(portfolioCoinItem) {

            }
        }

    }
}