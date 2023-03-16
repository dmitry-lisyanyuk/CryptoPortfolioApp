package com.batro.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.batro.R
import com.batro.ui.theme.CryptoAppTheme
import com.batro.ui.theme.onColor40
import com.batro.ui.theme.onColor80
import com.batro.data.model.Coin
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


@Composable
fun CoinItem(
    coin: Coin?,
    modifier: Modifier = Modifier,
    showChevron: Boolean = true,
    clickable: Boolean = true,
    onClick: () -> Unit
) {
    Row(
        modifier
            .fillMaxWidth()
            .height(48.dp)
            .clickable(onClick = onClick, enabled = clickable)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        val textModifier = Modifier.weight(1.0f)
        if (coin == null) {
            Text(
                stringResource(R.string.select_coin_item_holder),
                modifier = textModifier,
                color = onColor40(),
                fontSize = 16.sp
            )
        } else {
            val text = remember(coin.name, coin.symbol) {
                coin.name + " (" + coin.symbol.uppercase() + ")"
            }

            Image(
                painter = rememberAsyncImagePainter(
                    model = coin.icon,
                    placeholder = painterResource(R.drawable.ic_coin_holder)
                ),
                contentDescription = null,
                modifier = Modifier.size(28.dp)
            )
//            Image(
//                painter = painterResource(R.drawable.ic_cr_btc),
//                contentDescription = null,
//                modifier = Modifier.size(28.dp)
//            )
            Spacer(Modifier.width(16.dp))
            Text(
                text = text,
                modifier = textModifier,
                color = onColor80(),
                fontSize = 16.sp
            )

        }
        if (showChevron) {
            Image(
                painter = painterResource(R.drawable.ic_chevron),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun SelectableItem(
    selectText: String,
    icon: Painter?,
    title: String?,
    modifier: Modifier = Modifier,
    showChevron: Boolean = true,
    clickable: Boolean = true,
    onClick: () -> Unit
) {
    Row(
        modifier
            .fillMaxWidth()
            .height(48.dp)
            .clickable(onClick = onClick, enabled = clickable)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val textModifier = Modifier.weight(1.0f)
        if (icon != null && title != null) {
            Image(
                painter = icon,
                contentDescription = null,
                modifier = Modifier.size(28.dp)
            )
            Spacer(Modifier.width(16.dp))
            Text(
                text = title,
                modifier = textModifier,
                color = onColor80(),
                fontSize = 16.sp
            )
        } else {
            Text(
                selectText,
                modifier = textModifier,
                color = onColor40(),
                fontSize = 16.sp
            )
        }
        if (showChevron) {
            Image(
                painter = painterResource(R.drawable.ic_chevron),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CoinItemPreview(
    @PreviewParameter(CoinItemProvider::class) coin: Coin?
) = CryptoAppTheme {
    CoinItem(
        coin,
        showChevron = coin == null,
        onClick = {

        }
    )
}

class CoinItemProvider : PreviewParameterProvider<Coin?> {

    companion object {
        val btc = Coin("btc", "BTC", "BTC", "", 3294)


        val res =
            "[{\"symbol\":\"aico\",\"marketCap\":0,\"name\":\"Aicon\",\"icon\":\"https://assets.coingecko.com/coins/images/12892/large/5920.png?1603333534\",\"coingeckoId\":\"aicon\"},{\"symbol\":\"aid\",\"marketCap\":0,\"name\":\"AidCoin\",\"icon\":\"https://assets.coingecko.com/coins/images/2144/large/aid.png?1547036449\",\"coingeckoId\":\"aidcoin\"},{\"symbol\":\"aidi\",\"marketCap\":0,\"name\":\"Aidi Finance\",\"icon\":\"https://assets.coingecko.com/coins/images/18979/large/aidi_200.png?1634096167\",\"coingeckoId\":\"aidi-finance\"},{\"symbol\":\"aidi\",\"marketCap\":0,\"name\":\"Aidi Inu\",\"icon\":\"https://assets.coingecko.com/coins/images/16339/large/aidi_200.png?1634199020\",\"coingeckoId\":\"aidi-inu\"},{\"symbol\":\"aidoc\",\"marketCap\":0,\"name\":\"AI Doctor\",\"icon\":\"https://assets.coingecko.com/coins/images/2449/large/aidoc.png?1547036587\",\"coingeckoId\":\"ai-doctor\"},{\"symbol\":\"aidogemini\",\"marketCap\":0,\"name\":\"AI DogeMini\",\"icon\":\"https://assets.coingecko.com/coins/images/28995/large/Logo_200px_x_200px.jpg?1675823705\",\"coingeckoId\":\"ai-dogemini\"},{\"symbol\":\"adk\",\"marketCap\":0,\"name\":\"Aidos Kuneen\",\"icon\":\"https://assets.coingecko.com/coins/images/6077/large/Logoldpi.png?1600764971\",\"coingeckoId\":\"aidos-kuneen\"},{\"symbol\":\"ali\",\"marketCap\":0,\"name\":\"AiLink\",\"icon\":\"https://assets.coingecko.com/coins/images/5917/large/ailink-token.png?1547041855\",\"coingeckoId\":\"ailink-token\"},{\"symbol\":\"aimx\",\"marketCap\":0,\"name\":\"Aimedis\",\"icon\":\"https://assets.coingecko.com/coins/images/21316/large/Logo_-_2021-12-08T091940.073.png?1638926411\",\"coingeckoId\":\"aimedis-2\"},{\"symbol\":\"ain\",\"marketCap\":0,\"name\":\"AI Network\",\"icon\":\"https://assets.coingecko.com/coins/images/13211/large/AI_Network_Logo_200x200.png?1606205615\",\"coingeckoId\":\"ai-network\"},{\"symbol\":\"ainu\",\"marketCap\":0,\"name\":\"Ainu\",\"icon\":\"https://assets.coingecko.com/coins/images/17022/large/AINU.jpg?1629342905\",\"coingeckoId\":\"ainu-token\"},{\"symbol\":\"aion\",\"marketCap\":0,\"name\":\"Aion\",\"icon\":\"https://assets.coingecko.com/coins/images/1023/large/Aion_Currency_Symbol_Transparent_PNG.png?1575876687\",\"coingeckoId\":\"aion\"},{\"symbol\":\"aioz\",\"marketCap\":0,\"name\":\"AIOZ Network\",\"icon\":\"https://assets.coingecko.com/coins/images/14631/large/aioz-logo-200.png?1649237507\",\"coingeckoId\":\"aioz-network\"},{\"symbol\":\"aipad\",\"marketCap\":0,\"name\":\"AIPad\",\"icon\":\"https://assets.coingecko.com/coins/images/28894/large/JZadeHu.jpeg?1675214438\",\"coingeckoId\":\"aipad\"},{\"symbol\":\"aipro\",\"marketCap\":0,\"name\":\"AIPRO\",\"icon\":\"https://assets.coingecko.com/coins/images/28217/large/AIPro_logo_png.png?1668429140\",\"coingeckoId\":\"aipro\"},{\"symbol\":\"abl\",\"marketCap\":0,\"name\":\"Airbloc\",\"icon\":\"https://assets.coingecko.com/coins/images/4393/large/airbloc-protocol-logo.png?1547039734\",\"coingeckoId\":\"airbloc-protocol\"},{\"symbol\":\"air\",\"marketCap\":0,\"name\":\"AirCoin\",\"icon\":\"https://assets.coingecko.com/coins/images/16878/large/logo200.png?1625755457\",\"coingeckoId\":\"aircoin-2\"},{\"symbol\":\"airx\",\"marketCap\":0,\"name\":\"Aircoins\",\"icon\":\"https://assets.coingecko.com/coins/images/9201/large/Aircoins.png?1591615033\",\"coingeckoId\":\"aircoins\"},{\"symbol\":\"airi\",\"marketCap\":0,\"name\":\"aiRight\",\"icon\":\"https://assets.coingecko.com/coins/images/16428/large/alright.PNG?1623995586\",\"coingeckoId\":\"airight\"},{\"symbol\":\"airt\",\"marketCap\":0,\"name\":\"AirNFT\",\"icon\":\"https://assets.coingecko.com/coins/images/18370/large/AIRT-Logo.png?1631676503\",\"coingeckoId\":\"airnft-token\"},{\"symbol\":\"airpay\",\"marketCap\":0,\"name\":\"AirPay\",\"icon\":\"https://assets.coingecko.com/coins/images/23782/large/ys_Kuqq6_400x400.jpg?1645436414\",\"coingeckoId\":\"airpay\"},{\"symbol\":\"ast\",\"marketCap\":0,\"name\":\"AirSwap\",\"icon\":\"https://assets.coingecko.com/coins/images/1019/large/Airswap.png?1630903484\",\"coingeckoId\":\"airswap\"},{\"symbol\":\"airtnt\",\"marketCap\":0,\"name\":\"AirTnT\",\"icon\":\"https://assets.coingecko.com/coins/images/28328/large/airtnt.png?1669457251\",\"coingeckoId\":\"airtnt\"},{\"symbol\":\"aisc\",\"marketCap\":0,\"name\":\"Ai Smart Chain\",\"icon\":\"https://assets.coingecko.com/coins/images/28916/large/logo.jpg?1675387522\",\"coingeckoId\":\"ai-smart-chain\"},{\"symbol\":\"tai\",\"marketCap\":0,\"name\":\"AITravis\",\"icon\":\"https://assets.coingecko.com/coins/images/28933/large/ava_%283%29.png?1675500690\",\"coingeckoId\":\"aitravis\"},{\"symbol\":\"awo\",\"marketCap\":0,\"name\":\"AiWork\",\"icon\":\"https://assets.coingecko.com/coins/images/15373/large/aiwork.PNG?1620691299\",\"coingeckoId\":\"aiwork\"},{\"symbol\":\"aje\",\"marketCap\":0,\"name\":\"Ajeverse\",\"icon\":\"https://assets.coingecko.com/coins/images/21283/large/ajeverse.PNG?1638864443\",\"coingeckoId\":\"ajeverse\"},{\"symbol\":\"baju\",\"marketCap\":0,\"name\":\"Ajuna Network\",\"icon\":\"https://assets.coingecko.com/coins/images/28550/large/RprqQjxf_400x400.jpg?1671684115\",\"coingeckoId\":\"ajuna-network\"},{\"symbol\":\"akt\",\"marketCap\":0,\"name\":\"Akash Network\",\"icon\":\"https://assets.coingecko.com/coins/images/12785/large/akash-logo.png?1615447676\",\"coingeckoId\":\"akash-network\"},{\"symbol\":\"hachi\",\"marketCap\":0,\"name\":\"Akita DAO\",\"icon\":\"https://assets.coingecko.com/coins/images/28862/large/HACHI_now.png?1674983125\",\"coingeckoId\":\"akita-dao\"},{\"symbol\":\"akita\",\"marketCap\":0,\"name\":\"Akita Inu\",\"icon\":\"https://assets.coingecko.com/coins/images/14115/large/akita.png?1661666578\",\"coingeckoId\":\"akita-inu\"},{\"symbol\":\"akta\",\"marketCap\":0,\"name\":\"Akita Inu ASA\",\"icon\":\"https://assets.coingecko.com/coins/images/26832/large/akta.png?1660296546\",\"coingeckoId\":\"akita-inu-asa\"},{\"symbol\":\"akitax\",\"marketCap\":0,\"name\":\"Akitavax\",\"icon\":\"https://assets.coingecko.com/coins/images/23043/large/coingeckologoakitax.png?1643100394\",\"coingeckoId\":\"akitavax\"},{\"symbol\":\"aki\",\"marketCap\":0,\"name\":\"Akitsuki\",\"icon\":\"https://assets.coingecko.com/coins/images/27746/large/200px-aki.png?1671681485\",\"coingeckoId\":\"akitsuki\"},{\"symbol\":\"akn\",\"marketCap\":0,\"name\":\"Akoin\",\"icon\":\"https://assets.coingecko.com/coins/images/13098/large/akoin_logo.jpg?1605087833\",\"coingeckoId\":\"akoin\"},{\"symbol\":\"aka\",\"marketCap\":0,\"name\":\"Akroma\",\"icon\":\"https://assets.coingecko.com/coins/images/4455/large/akroma-logo-1000x1000_preview.png?1547039777\",\"coingeckoId\":\"akroma\"},{\"symbol\":\"akro\",\"marketCap\":0,\"name\":\"Akropolis\",\"icon\":\"https://assets.coingecko.com/coins/images/3328/large/Akropolis.png?1547037929\",\"coingeckoId\":\"akropolis\"},{\"symbol\":\"adel\",\"marketCap\":0,\"name\":\"Delphi\",\"icon\":\"https://assets.coingecko.com/coins/images/12300/large/adel_on_white_10x.png?1598967061\",\"coingeckoId\":\"akropolis-delphi\"},{\"symbol\":\"aktio\",\"marketCap\":0,\"name\":\"Aktio\",\"icon\":\"https://assets.coingecko.com/coins/images/25049/large/0shF1qhT_400x400.jpg?1649914357\",\"coingeckoId\":\"aktio\"},{\"symbol\":\"acrv\",\"marketCap\":0,\"name\":\"Aladdin cvxCRV\",\"icon\":\"https://assets.coingecko.com/coins/images/25395/large/Sv06cFHS_400x400.jpg?1651707422\",\"coingeckoId\":\"aladdin-cvxcrv\"},{\"symbol\":\"ald\",\"marketCap\":0,\"name\":\"Aladdin DAO\",\"icon\":\"https://assets.coingecko.com/coins/images/18277/large/78200839.png?1631234134\",\"coingeckoId\":\"aladdin-dao\"},{\"symbol\":\"ala\",\"marketCap\":0,\"name\":\"Alanyaspor Fan Token\",\"icon\":\"https://assets.coingecko.com/coins/images/18966/large/spV2Ly3gIaB9XVdib9FUJVPvfT6KBq2qQttTH275.png?1634028195\",\"coingeckoId\":\"alanyaspor-fan-token\"},{\"symbol\":\"atp\",\"marketCap\":0,\"name\":\"Alaya\",\"icon\":\"https://assets.coingecko.com/coins/images/14309/large/atp.PNG?1615362417\",\"coingeckoId\":\"alaya\"},{\"symbol\":\"albedo\",\"marketCap\":0,\"name\":\"ALBEDO\",\"icon\":\"https://assets.coingecko.com/coins/images/28024/large/217d99e9-8a21-4678-9019-6b3cf7f714fe.png?1667031419\",\"coingeckoId\":\"albedo\"},{\"symbol\":\"alcazar\",\"marketCap\":0,\"name\":\"Alcazar\",\"icon\":\"https://assets.coingecko.com/coins/images/28010/large/Alcazar.jpeg?1666943350\",\"coingeckoId\":\"alcazar\"},{\"symbol\":\"mist\",\"marketCap\":0,\"name\":\"Alchemist\",\"icon\":\"https://assets.coingecko.com/coins/images/14655/large/79158662.png?1617589045\",\"coingeckoId\":\"alchemist\"},{\"symbol\":\"alcx\",\"marketCap\":0,\"name\":\"Alchemix\",\"icon\":\"https://assets.coingecko.com/coins/images/14113/large/Alchemix.png?1614409874\",\"coingeckoId\":\"alchemix\"},{\"symbol\":\"aleth\",\"marketCap\":0,\"name\":\"Alchemix ETH\",\"icon\":\"https://assets.coingecko.com/coins/images/16271/large/uS04dyYy_400x400.jpeg?1663051098\",\"coingeckoId\":\"alchemix-eth\"},{\"symbol\":\"alusd\",\"marketCap\":0,\"name\":\"Alchemix USD\",\"icon\":\"https://assets.coingecko.com/coins/images/14114/large/Alchemix_USD.png?1614410406\",\"coingeckoId\":\"alchemix-usd\"},{\"symbol\":\"ach\",\"marketCap\":0,\"name\":\"Alchemy Pay\",\"icon\":\"https://assets.coingecko.com/coins/images/12390/large/ACH_%281%29.png?1599691266\",\"coingeckoId\":\"alchemy-pay\"}]"

        val lists by lazy {
            val itemType = object : TypeToken<List<Coin>>() {}.type
            Gson().fromJson<List<Coin>>(res, itemType)
        }
    }

    override val values = listOf(btc, null).asSequence()
}