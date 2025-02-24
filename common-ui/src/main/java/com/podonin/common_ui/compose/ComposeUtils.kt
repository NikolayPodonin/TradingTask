package com.podonin.common_ui.compose

import androidx.annotation.DimenRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun Int.toDp(): Dp {
    return with(LocalDensity.current) { toDp() }
}

@Composable
fun textSizeResource(@DimenRes id: Int): TextUnit {
    return dimensionResource(id = id).value.sp
}
