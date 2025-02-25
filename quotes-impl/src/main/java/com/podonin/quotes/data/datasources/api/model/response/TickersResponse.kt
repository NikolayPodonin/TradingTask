package com.podonin.quotes.data.datasources.api.model.response

import kotlinx.serialization.Serializable

@Serializable
data class TickersResponse(
    val tickers: List<String>
)