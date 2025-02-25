package com.podonin.quotes.data.repository

import com.podonin.common_io.DispatchersHolder
import com.podonin.quotes.data.datasources.database.QuotesDBDataSource
import com.podonin.quotes.data.datasources.socket.QuoteSocketDataSource
import com.podonin.quotes.data.mapper.toModel
import com.podonin.quotes.domain.model.Quote
import com.podonin.quotes.domain.repository.QuotesRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuotesRepositoryImpl @Inject constructor(
    private val dispatchersHolder: DispatchersHolder,
    private val quoteSocketDataSource: QuoteSocketDataSource,
    private val quotesDBDataSource: QuotesDBDataSource
) : QuotesRepository {

    override suspend fun subscribeOnQuotes(paperList: List<String>) {
        coroutineScope {
            launch(dispatchersHolder.io) {
                quoteSocketDataSource.subscribeOnQuotes(paperList).collectLatest { quote ->
                    quotesDBDataSource.insertOrUpdate(quote)
                }
            }
        }
    }

    override fun getQuotesFlow(): Flow<List<Quote>> {
        return quotesDBDataSource.getQuotesFlow().map {
            it.map { quoteEntity ->
                quoteEntity.toModel()
            }
        }
    }
}