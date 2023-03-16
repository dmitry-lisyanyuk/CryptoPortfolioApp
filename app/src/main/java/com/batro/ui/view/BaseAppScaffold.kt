package com.batro.ui.view

import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.batro.R
import com.batro.ui.theme.CryptoAppTheme
import com.batro.ui.theme.onColor80


@Composable
fun BaseAppScaffold(
    title: String,
    modifier: Modifier = Modifier,
    showBackButton: Boolean = true,
    contentToolbar: @Composable (RowScope.() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit
) {
    Column(modifier.fillMaxWidth()) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (showBackButton) {
                val context = LocalContext.current
                Image(
                    painter = painterResource(R.drawable.ic_arrow_left),
                    contentDescription = "back",
                    modifier = Modifier
                        .size(56.dp)
                        .clickable(onClick = {
                            (context as? ComponentActivity)?.onBackPressedDispatcher?.onBackPressed()
                        })
                        .padding(16.dp)
                )
            } else {
                Spacer(modifier = Modifier.width(16.dp))
            }
            Text(text = title, color = onColor80(), fontSize = 24.sp)
            contentToolbar?.invoke(this)
        }
        Divider()
        Box(
            Modifier
                .fillMaxWidth()
        ) {
            content.invoke(this)
        }
    }

}

@Preview(showBackground = true)
@Composable
fun BaseAppScaffoldPreview() = CryptoAppTheme {
    BaseAppScaffold(
        title = "BaseAppScaffoldPreview",
        showBackButton = true
    ) {
        Text("Content", Modifier.padding(16.dp))
    }
}