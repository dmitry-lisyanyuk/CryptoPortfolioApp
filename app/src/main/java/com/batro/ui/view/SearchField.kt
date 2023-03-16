@file:OptIn(ExperimentalMaterial3Api::class)

package com.batro.ui.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.batro.R


@Composable
fun SearchField(
    value: String,
    onValueChange: (String) -> Unit
) {
    AppTextField(
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp)
            .padding(horizontal = 16.dp),
        value = value,
        shape = RoundedCornerShape(25.dp),
        onValueChange = onValueChange,
        placeholder = { Text(stringResource(R.string.base_search)) },
        colors = TextFieldDefaults.textFieldColors(
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 6.dp)
    )
}