package com.podonin.quotes.data.api.model.request

import kotlinx.serialization.Serializable

@Serializable
data class TickersBaseRequest(
    val q: TickersRequest
)

@Serializable
data class TickersRequest(
    val cmd: String,
    val params: TickersRequestParams
)