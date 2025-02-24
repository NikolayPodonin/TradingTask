package com.podonin.quotes.data.api.model.response

import kotlinx.serialization.Serializable

@Serializable
data class TickersResponse(
    val tickers: List<String>
)