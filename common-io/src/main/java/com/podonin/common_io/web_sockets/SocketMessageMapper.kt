package com.podonin.common_io.web_sockets

interface SocketMessageMapper<T> {
    fun map(message: String): T?
}