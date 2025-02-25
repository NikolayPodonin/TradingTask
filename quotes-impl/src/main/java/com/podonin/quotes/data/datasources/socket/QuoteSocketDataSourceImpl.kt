package com.podonin.quotes.data.datasources.socket

import android.util.Log
import com.podonin.common_io.web_sockets.DefaultSocketListener
import com.podonin.common_io.web_sockets.WebSocketClient
import com.podonin.common_utils.mutableSharedFlow
import com.podonin.quotes.data.mapper.mapToRequest
import com.podonin.quotes.data.model.QuoteEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.decodeFromJsonElement
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

class QuoteSocketDataSourceImpl @Inject constructor(
    private val webSocketClient: WebSocketClient,
) : QuoteSocketDataSource {
    
    private val quoteFlow = mutableSharedFlow<QuoteEntity>()
    private val socketListener = object : DefaultSocketListener() {
        override fun onEvent(event: String, data: JsonElement?) {
            parseEvent(event, data)
        }
        override fun onDisconnected() { webSocketClient.close() }
    }

    override suspend fun subscribeOnQuotes(papers: List<String>): Flow<QuoteEntity> {
        CoroutineScope(coroutineContext).launch {
            val subscribeRequest = (REQUEST_EVENT to papers).mapToRequest()
            webSocketClient.connect(subscribeRequest, socketListener)
        }
        return quoteFlow
    }

    private fun parseEvent(event: String, data: JsonElement?) {
        try {
            if (event == RESPONSE_EVENT && data != null) {
                val quote = Json.decodeFromJsonElement<QuoteEntity>(data)
                quoteFlow.tryEmit(quote)
            }
        } catch (ex: SerializationException) {
            Log.e("QuoteSocketListener", "Error parsing JSON: ${ex.message}")
        }
    }

    companion object {
        private const val RESPONSE_EVENT = "q"
        private const val REQUEST_EVENT = "realtimeQuotes"
    }
}