package com.batro.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.batro.ui.theme.CryptoAppTheme
import com.batro.ui.theme.onColor40
import com.batro.ui.theme.onColor80

@Composable
fun InfoItem(
    titleLeft: String,
    valueLeft: String,
    titleRight: String,
    valueRight: String
) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(58.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(text = titleLeft, color = onColor40(), fontSize = 12.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = valueLeft, color = onColor80(), fontSize = 16.sp)
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(text = titleRight, color = onColor40(), fontSize = 12.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = valueRight, color = onColor80(), fontSize = 16.sp)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun InfoItemPreview() = CryptoAppTheme {
    InfoItem(
        titleLeft = "Avg Buy",
        valueLeft = "\$346.06",
        titleRight = "Avg sell",
        valueRight = "\$360.34",
    )
}