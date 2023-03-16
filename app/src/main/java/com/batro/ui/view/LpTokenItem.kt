package com.batro.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.batro.R
import com.batro.ui.theme.onColor40
import com.batro.ui.theme.onColor80

import com.batro.data.model.LPToken

@Composable
fun LpTokenItem(
    lpToken: LPToken?,
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
        if (lpToken == null) {
            Text(
                stringResource(R.string.select_lp_token_item_holder),
                modifier = textModifier,
                color = onColor40(),
                fontSize = 16.sp
            )
        } else {
            val text = remember(lpToken) {
                lpToken.toString()
            }
            Box(modifier = Modifier.size(30.dp)) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = lpToken.coin1.icon,
                        placeholder = painterResource(R.drawable.ic_coin_holder)
                    ),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Image(
                    painter = rememberAsyncImagePainter(
                        model = lpToken.coin0.icon,
                        placeholder = painterResource(R.drawable.ic_coin_holder)
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .size(22.dp)
                        .align(Alignment.BottomEnd)
                )
            }

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