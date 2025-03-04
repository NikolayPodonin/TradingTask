package com.podonin.common_io.web_sockets

import kotlinx.serialization.json.JsonElement

data class SocketEvent(
    val event: String,
    val data: JsonElement?
)
