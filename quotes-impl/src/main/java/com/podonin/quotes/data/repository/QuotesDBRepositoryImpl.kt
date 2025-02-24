package com.podonin.quotes.data.repository

import com.podonin.quotes.data.database.dao.QuoteDao
import com.podonin.quotes.data.mapper.toModel
import com.podonin.quotes_api.data.model.Quote
import com.podonin.quotes_api.data.model.QuotesDBRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuotesDBRepositoryImpl @Inject constructor(
    private val quoteDao: QuoteDao
) : QuotesDBRepository {

    override fun getQuotesFlow(): Flow<List<Quote>> {
        return quoteDao.getAllQuotes().map { entities ->
            entities.map { it.toModel() }
        }
    }
}