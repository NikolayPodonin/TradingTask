package com.podonin.quotes.data.mapper

import com.podonin.quotes.data.model.QuoteEntity
import com.podonin.quotes.domain.model.Quote

fun QuoteEntity.toModel() = Quote(
    ticker = ticker,
    name = name,
    lastTradeExchange = lastTradeExchange,
    lastTradePrice = lastTradePrice,
    lastTradeChange = lastTradeChange,
    lastTradeChangePercent = lastTradeChangePercent,
    minStep = minStep
)