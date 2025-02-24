package com.podonin.quotes.data.mapper

import com.podonin.quotes.data.database.entity.QuoteEntity
import com.podonin.quotes_api.data.model.Quote

fun Quote.toEntity() = QuoteEntity(
    ticker = ticker,
    lastTradeExchange = lastTradeExchange,
    name = name,
    lastTradePrice = lastTradePrice,
    lastTradeChange = lastTradeChange,
    lastTradeChangePercent = lastTradeChangePercent,
    minStep = minStep
)

fun QuoteEntity.toModel() = Quote(
    ticker = ticker,
    name = name,
    lastTradeExchange = lastTradeExchange,
    lastTradePrice = lastTradePrice,
    lastTradeChange = lastTradeChange,
    lastTradeChangePercent = lastTradeChangePercent,
    minStep = minStep
)