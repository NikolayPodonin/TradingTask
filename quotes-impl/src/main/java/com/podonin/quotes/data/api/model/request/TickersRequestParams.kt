package com.podonin.quotes.data.api.model.request

import kotlinx.serialization.Serializable

@Serializable
data class TickersRequestParams(
    val exchange: String,
    val gainers: Int,
    val limit: Int,
    val type: String
)