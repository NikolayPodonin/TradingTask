package com.podonin.quotes.data.datasources.socket

import android.util.Log
import com.podonin.common_io.web_sockets.DefaultSocketConnectionListener
import com.podonin.common_io.web_sockets.WebSocketClient
import com.podonin.quotes.data.mapper.mapToRequest
import com.podonin.quotes.data.model.QuoteEntity
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.decodeFromJsonElement
import javax.inject.Inject

class QuoteSocketDataSourceImpl @Inject constructor(
    private val webSocketClient: WebSocketClient,
) : QuoteSocketDataSource {

    override val quotesFlow = webSocketClient.eventsFlow
        .filter { it.event == RESPONSE_EVENT }
        .mapNotNull {
            parseData(it.data)
        }

    private val socketConnectionListener = object : DefaultSocketConnectionListener() {
        override fun onDisconnected() {
            webSocketClient.close()
        }
    }

    override suspend fun launchQuotesWS(papers: List<String>) {
        val subscribeRequest = (REQUEST_EVENT to papers).mapToRequest()
        webSocketClient.connect(subscribeRequest, socketConnectionListener)
    }

    private fun parseData(data: JsonElement?): QuoteEntity? {
        return try {
            data?.let {
                Json.decodeFromJsonElement<QuoteEntity>(it)
            }
        } catch (ex: SerializationException) {
            Log.e("QuoteSocketListener", "Error parsing JSON: ${ex.message}")
            null
        }
    }

    companion object {
        private const val RESPONSE_EVENT = "q"
        private const val REQUEST_EVENT = "realtimeQuotes"
    }
}