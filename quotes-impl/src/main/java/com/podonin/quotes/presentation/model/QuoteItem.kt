package com.podonin.quotes.presentation.model

data class QuoteItem(
    val ticker: String,
    val logoUrl: String,
    val exchangeAndName: String,
    val percent: String,
    val priceAndChange: String,
    val isPositive: Boolean,
    val actualPrice: Double
)
