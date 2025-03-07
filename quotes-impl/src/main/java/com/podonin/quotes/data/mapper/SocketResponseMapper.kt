package com.podonin.quotes.data.mapper

import android.util.Log
import com.podonin.common_io.web_sockets.SocketMessageMapper
import com.podonin.quotes.data.model.QuoteEntity
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonPrimitive
import javax.inject.Inject

class SocketResponseMapper @Inject constructor(): SocketMessageMapper<QuoteEntity> {

    override fun map(message: String): QuoteEntity? {
        return parseMessage(message)
    }

    private fun parseMessage(message: String): QuoteEntity? {
        try {
            val jsonElement = Json.parseToJsonElement(message)

            if (jsonElement is JsonArray && jsonElement.size == 2) {
                val eventType = jsonElement[0].jsonPrimitive.content
                val data = jsonElement[1]
                return if (eventType == RESPONSE_EVENT) {
                    parseData(data)
                } else {
                    null
                }
            }
        } catch (e: SerializationException) {
            Log.e("SocketResponseMapper", "Error parsing WebSocket message: ${e.message}")
        }
        return null
    }

    private fun parseData(data: JsonElement?): QuoteEntity? {
        return data?.let {
            Json.decodeFromJsonElement<QuoteEntity>(it)
        }
    }

    companion object {
        private const val RESPONSE_EVENT = "q"
    }
}