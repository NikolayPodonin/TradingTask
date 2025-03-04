package com.podonin.common_io.web_sockets

import android.util.Log
import com.podonin.common_utils.mutableSharedFlow
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.wss
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.coroutines.delay
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.jsonPrimitive

class WebSocketClient(private val url: String) {

    private val client = HttpClient(OkHttp) {
        install(WebSockets)
    }

    val eventsFlow = mutableSharedFlow<SocketEvent>(
        replay = 100,
        extraBufferCapacity = 100
    )

    suspend fun connect(
        subscribeMessage: String,
        listener: SocketConnectionListener,
        retries: Int = MAX_RETRIES,
        delayMillis: Long = RETRY_DELAY
    ) {
        var attempt = 0
        while (attempt < retries) {
            try {
                startSocket(subscribeMessage, listener)
                break
            } catch (e: Exception) {
                attempt++
                delay(delayMillis * attempt)
            }
        }
        listener.onDisconnected()
    }

    fun close() {
        client.close()
    }

    private suspend fun startSocket(
        subscribeMessage: String,
        listener: SocketConnectionListener
    ) {
        try {
            client.wss(url) {
                send(Frame.Text(subscribeMessage))

                for (frame in incoming) {
                    when (frame) {
                        is Frame.Text -> {
                            parseMessage(frame.readText())?.let {
                                eventsFlow.tryEmit(it)
                            }
                        }
                        is Frame.Close -> {
                            connect(subscribeMessage, listener)
                        }
                        else -> Unit
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("WebSocketClient", "Error connecting WebSocket: ${e.message}")
        }
    }

    private fun parseMessage(message: String): SocketEvent? {
        try {
            val jsonElement = Json.parseToJsonElement(message)

            if (jsonElement is JsonArray && jsonElement.size == 2) {
                val eventType = jsonElement[0].jsonPrimitive.content
                val data = jsonElement[1]
                return SocketEvent(eventType, data)
            }
        } catch (e: SerializationException) {
            Log.e("WebSocketClient", "Error parsing WebSocket message: ${e.message}")
        }
        return null
    }

    companion object {
        private const val MAX_RETRIES = 5
        private const val RETRY_DELAY = 2000L
    }
}