package com.batro.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


val primaryColor = Color(0xFFFFC700)

@Composable
fun onColor80() = if (isSystemInDarkTheme()) lightColor80 else darkColor80

@Composable
fun onColor40() = if (isSystemInDarkTheme()) lightColor40 else darkColor40

val darkColor80 = Color(0xCC0E1114)
val darkColor40 = Color(0x660E1114)
val darkColor10 = Color(0x190E1114)
val darkColor05 = Color(0x0D0E1114)

val lightColor80 = Color(0xCCF1EEEB)
val lightColor40 = Color(0x66F1EEEB)
val lightColor10 = Color(0x19F1EEEB)
val lightColor05 = Color(0x0DF1EEEB)


val grayColor = Color(0xFF757575)
val grayColor10 = Color(0x19757575)
val greenColor = Color(0xFF33D60A)
val greenColor10 = Color(0x1933D60A)
val redColor = Color(0xFFD60A0A)
val redColor10 = Color(0x19D60A0A)
