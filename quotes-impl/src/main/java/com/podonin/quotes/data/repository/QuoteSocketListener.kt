package com.podonin.quotes.data.repository

import android.util.Log
import com.podonin.common_io.web_sockets.DefaultSocketListener
import com.podonin.common_utils.mutableSharedFlow
import com.podonin.quotes_api.data.model.Quote
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.decodeFromJsonElement
import javax.inject.Inject

class QuoteSocketListener @Inject constructor() : DefaultSocketListener() {
    
    private val _quoteFlow = mutableSharedFlow<Quote>()
    val quoteFlow = _quoteFlow.asSharedFlow()
    private val _disconnectFlow = mutableSharedFlow<Unit>()
    val disconnectFlow = _disconnectFlow.asSharedFlow()

    override fun onEvent(event: String, data: JsonElement?) {
        try {
            if (event == RESPONSE_EVENT && data != null) {
                val quote = Json.decodeFromJsonElement<Quote>(data)
                _quoteFlow.tryEmit(quote)
            }
        } catch (ex: SerializationException) {
            Log.e("QuoteSocketListener", "Error parsing JSON: ${ex.message}")
        }
    }

    override fun onDisconnected() {
        _disconnectFlow.tryEmit(Unit)
    }

    companion object {
        private const val RESPONSE_EVENT = "q"
    }
}