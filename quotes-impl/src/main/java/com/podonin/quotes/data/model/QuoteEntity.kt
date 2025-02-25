package com.podonin.quotes.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

@OptIn(ExperimentalSerializationApi::class)
@Serializable @JsonIgnoreUnknownKeys
@Entity(tableName = "quotes")
data class QuoteEntity(
    @PrimaryKey @SerialName("c") val ticker: String, // Тикер
    @SerialName("name") val lastTradeExchange: String? = null, // Биржа последней сделки
    @SerialName("ltr") val name: String? = null, // Название бумаги
    @SerialName("ltp") val lastTradePrice: Double? = null, // Цена последней сделки
    @SerialName("chg") val lastTradeChange: Double? = null, // Изменение цены последней сделки в пунктах относительно цены закрытия предыдущей торговой сессии
    @SerialName("pcp") val lastTradeChangePercent: Double? = null, // Изменение в процентах относительно цены закрытия предыдущей торговой сессии
    @SerialName("min_step") val minStep: Double? = null, // Минимальный шаг цены
)