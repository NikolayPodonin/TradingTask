package com.podonin.quotes.domain

import com.podonin.quotes.domain.model.Quote
import kotlinx.coroutines.flow.Flow

interface QuotesInteractor {
    suspend fun subscribeOnQuotes()
    fun getQuotesFlow(): Flow<List<Quote>>
    fun getErrorFlow(): Flow<String?>
}