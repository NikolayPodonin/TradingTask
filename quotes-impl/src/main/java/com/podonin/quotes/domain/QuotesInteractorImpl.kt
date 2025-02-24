package com.podonin.quotes.domain

import android.util.Log
import com.podonin.quotes.domain.repository.QuotesRepository
import com.podonin.quotes.domain.repository.TickersRepository
import com.podonin.quotes_api.data.model.Quote
import com.podonin.quotes_api.data.model.QuotesDBRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class QuotesInteractorImpl @Inject constructor(
    private val quotesRepository: QuotesRepository,
    private val quotesDBRepository: QuotesDBRepository,
    private val tickersRepository: TickersRepository
) : QuotesInteractor {
    override suspend fun subscribeOnQuotes() {
        val paperList = tickersRepository.getTickers(
            type = "stocks",
            exchange = "russia",
            limit = 30,
            gainers = 0
        )
        Log.d("QuotesInteractor", paperList.toString())
        quotesRepository.subscribeOnQuotes(paperList)
    }

    override fun getQuotesFlow(): Flow<List<Quote>> {
        return quotesDBRepository.getQuotesFlow()
    }
}