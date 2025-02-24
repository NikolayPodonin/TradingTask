package com.podonin.quotes.domain

import com.podonin.quotes_api.data.model.Quote
import kotlinx.coroutines.flow.Flow

interface QuotesInteractor {
    suspend fun subscribeOnQuotes()
    fun getQuotesFlow(): Flow<List<Quote>>
}