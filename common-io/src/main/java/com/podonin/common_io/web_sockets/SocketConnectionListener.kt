package com.podonin.common_io.web_sockets

interface SocketConnectionListener {
    fun onConnected()
    fun onDisconnected()
}

open class DefaultSocketConnectionListener : SocketConnectionListener {
    override fun onConnected() = Unit
    override fun onDisconnected() = Unit
}