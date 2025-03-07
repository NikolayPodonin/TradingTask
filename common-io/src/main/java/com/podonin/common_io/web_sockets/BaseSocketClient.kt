package com.podonin.common_io.web_sockets

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.wss
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharedFlow

abstract class BaseSocketClient<T>(
    protected val url: String,
    protected val messageMapper: SocketMessageMapper<T>
) {

    private val client = HttpClient(OkHttp) {
        install(WebSockets)
    }

    abstract val resultFlow: SharedFlow<T>

    open fun onConnected() = Unit
    open fun onDisconnected() = Unit
    abstract suspend fun onMessage(message: String)

    suspend fun connect(
        subscribeMessage: String,
        retries: Int = MAX_RETRIES,
        delayMillis: Long = RETRY_DELAY
    ) {
        var attempt = 0
        while (attempt < retries) {
            try {
                startSocket(subscribeMessage)
                break
            } catch (e: Exception) {
                attempt++
                delay(delayMillis * attempt)
            }
        }
        onDisconnected()
    }

    fun close() {
        client.close()
    }

    private suspend fun startSocket(
        subscribeMessage: String
    ) {
        try {
            client.wss(url) {
                send(Frame.Text(subscribeMessage))
                onConnected()

                for (frame in incoming) {
                    when (frame) {
                        is Frame.Text -> {
                            onMessage(frame.readText())
                        }
                        is Frame.Close -> {
                            connect(subscribeMessage)
                        }
                        else -> Unit
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("WebSocketClient", "Error connecting WebSocket: ${e.message}")
        }
    }

    companion object {
        private const val MAX_RETRIES = 5
        private const val RETRY_DELAY = 2000L
    }
}