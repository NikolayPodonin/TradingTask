package com.podonin.quotes.domain.repository

import com.podonin.quotes.domain.model.Quote
import kotlinx.coroutines.flow.Flow

interface QuotesRepository {
    suspend fun subscribeOnQuotes(paperList: List<String>)
    fun getQuotesFlow(): Flow<List<Quote>>
}