package com.podonin.common_io.web_sockets

import kotlinx.serialization.json.JsonElement

interface SocketListener {
    fun onConnected()
    fun onEvent(event: String, data: JsonElement?)
    fun onDisconnected()
}

open class DefaultSocketListener : SocketListener {
    override fun onConnected() = Unit
    override fun onEvent(event: String, data: JsonElement?) = Unit
    override fun onDisconnected() = Unit
}