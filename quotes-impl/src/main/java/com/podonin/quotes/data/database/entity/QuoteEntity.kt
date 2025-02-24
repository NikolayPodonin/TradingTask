package com.podonin.quotes.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quotes")
data class QuoteEntity(
    @PrimaryKey val ticker: String, // Тикер
    val lastTradeExchange: String?, // Биржа последней сделки
    val name: String?, // Название бумаги
    val lastTradePrice: Double?, // Цена последней сделки
    val lastTradeChange: Double?, // Изменение цены последней сделки в пунктах относительно цены закрытия предыдущей торговой сессии
    val lastTradeChangePercent: Double?, // Изменение в процентах относительно цены закрытия предыдущей торговой сессии
    val minStep: Double?, // Минимальный шаг цены
)