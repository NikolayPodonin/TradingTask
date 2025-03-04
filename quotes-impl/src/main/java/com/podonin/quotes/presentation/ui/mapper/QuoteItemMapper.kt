package com.podonin.quotes.presentation.ui.mapper

import com.podonin.common_ui.resources_provider.ResourcesProvider
import com.podonin.common_utils.orZero
import com.podonin.common_utils.roundToStep
import com.podonin.quotes.domain.model.Quote
import com.podonin.quotes.presentation.ui.model.QuoteItem
import com.podonin.quotes_impl.R
import javax.inject.Inject

class QuoteItemMapper @Inject constructor(
    private val resourcesProvider: ResourcesProvider
) {
    fun mapToItems(quotes: List<Quote>): List<QuoteItem> {
        return quotes.map { quote -> quote.toItem() }
    }

    private fun Quote.toItem(): QuoteItem {
        return QuoteItem(
            ticker = ticker,
            logoUrl = logoUrl(),
            exchangeAndName = exchangeAndName(),
            percent = percent(),
            priceAndChange = priceAndChange(),
            isPositive = isPositiveChange(),
            actualPrice = lastTradePrice.orZero()
        )
    }

    private fun Quote.logoUrl() = resourcesProvider.getString(
        R.string.quotes_logo_url_prefix,
        ticker.lowercase(),
    )

    private fun Quote.exchangeAndName() = when {
        lastTradeExchange.isNullOrEmpty() -> name.orEmpty()
        name.isNullOrEmpty() -> lastTradeExchange
        else -> resourcesProvider.getString(
            R.string.quotes_exchange_and_name,
            lastTradeExchange,
            name
        )
    }

    private fun Quote.percent() = resourcesProvider.getString(
        R.string.quotes_percent,
        lastTradeChangePercent.orZero()
    )

    private fun Quote.priceAndChange() = resourcesProvider.getString(
        R.string.quotes_price_and_change,
        lastTradePrice.orZero().roundToStep(minStep.orZero()),
        lastTradeChange.orZero().roundToStep(minStep.orZero())
    )

    private fun Quote.isPositiveChange() = lastTradeChangePercent.orZero() >= 0.0
}