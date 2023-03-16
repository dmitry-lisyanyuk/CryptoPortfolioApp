@file:OptIn(ExperimentalMaterialApi::class)

package com.batro.ui.view


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DefaultBottomSheetLayout(
    title: String,
    modalSheetState: ModalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true,
    ),
    sheetContent: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetShape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
        sheetBackgroundColor = MaterialTheme.colorScheme.surface,
        sheetContent = {
            BaseAppScaffold(
                title = title,
                showBackButton = false,
                modifier = Modifier.fillMaxWidth()
            ) {
                sheetContent.invoke()
            }
        },
        content = content
    )
}