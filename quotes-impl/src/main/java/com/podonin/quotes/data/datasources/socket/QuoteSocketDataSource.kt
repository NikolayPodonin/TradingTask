package com.podonin.quotes.data.datasources.socket

import com.podonin.quotes.data.model.QuoteEntity
import kotlinx.coroutines.flow.Flow

interface QuoteSocketDataSource {
    suspend fun subscribeOnQuotes(papers: List<String>): Flow<QuoteEntity>
}