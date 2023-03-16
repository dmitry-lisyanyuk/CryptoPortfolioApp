package com.batro.util.extension

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController


@ExperimentalMaterialApi
@Composable
fun rememberDefaultModalBottomSheetState(
    initialValue: ModalBottomSheetValue = ModalBottomSheetValue.Hidden
) = rememberModalBottomSheetState(
    initialValue = initialValue,
    confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
    skipHalfExpanded = true,
)

@Composable
fun <T> NavController.GetOnceResult(keyResult: String, onResult: (T) -> Unit) {
    val valueScreenResult = currentBackStackEntry
        ?.savedStateHandle
        ?.getLiveData<T>(keyResult)?.observeAsState()

    valueScreenResult?.value?.let {
        onResult(it)

        currentBackStackEntry
            ?.savedStateHandle
            ?.remove<T>(keyResult)
    }
}

fun <T> NavController.setResult(keyResult: String, result: T) {
    previousBackStackEntry
        ?.savedStateHandle
        ?.set(keyResult, result)
}