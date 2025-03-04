package com.podonin.quotes.data.datasources.socket

import com.podonin.quotes.data.model.QuoteEntity
import kotlinx.coroutines.flow.Flow

interface QuoteSocketDataSource {
    val quotesFlow: Flow<QuoteEntity>
    suspend fun launchQuotesWS(papers: List<String>)
}