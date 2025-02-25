package com.podonin.quotes.data.datasources.database

import com.podonin.quotes.data.datasources.database.dao.QuoteDao
import com.podonin.quotes.data.model.QuoteEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuotesDBDataSourceImpl @Inject constructor(
    private val quoteDao: QuoteDao
) : QuotesDBDataSource {

    override suspend fun insertOrUpdate(quote: QuoteEntity) {
        val existed = quoteDao.getQuote(quote.ticker)
        if (existed == null) {
            quoteDao.insert(quote)
        } else {
            quoteDao.update(
                quote.ticker,
                quote.lastTradeExchange,
                quote.name,
                quote.lastTradePrice,
                quote.lastTradeChange,
                quote.lastTradeChangePercent,
                quote.minStep
            )
        }
    }

    override fun getQuotesFlow(): Flow<List<QuoteEntity>> {
        return quoteDao.getAllQuotes()
    }
}