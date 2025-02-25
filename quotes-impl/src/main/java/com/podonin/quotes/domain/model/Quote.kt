package com.podonin.quotes.domain.model

data class Quote(
    val ticker: String, // Тикер
    val name: String? = null, // Название бумаги
    val lastTradeExchange: String? = null, // Биржа последней сделки
    val lastTradePrice: Double? = null, // Цена последней сделки
    val lastTradeChange: Double? = null, // Изменение цены последней сделки в пунктах относительно цены закрытия предыдущей торговой сессии
    val lastTradeChangePercent: Double? = null, // Изменение в процентах относительно цены закрытия предыдущей торговой сессии
    val minStep: Double? = null, // Минимальный шаг цены
)