package com.podonin.quotes.data.datasources.database

import com.podonin.quotes.data.model.QuoteEntity
import kotlinx.coroutines.flow.Flow

interface QuotesDBDataSource {
    suspend fun insertOrUpdate(quote: QuoteEntity)
    fun getQuotesFlow(): Flow<List<QuoteEntity>>
}