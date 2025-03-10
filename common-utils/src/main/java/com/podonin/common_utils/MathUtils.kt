package com.podonin.common_utils

import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.ceil
import kotlin.math.log10

fun Double?.orZero() = this ?: 0.0

fun Double.roundToStep(step: Double): String {
    val scale = if (step <= 0.0) {
        2
    } else {
        ceil(-log10(step)).toInt().coerceAtLeast(0)
    }
    return BigDecimal(this)
        .setScale(scale, RoundingMode.HALF_UP)
        .stripTrailingZeros()
        .toPlainString()
}