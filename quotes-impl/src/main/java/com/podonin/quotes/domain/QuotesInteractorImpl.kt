package com.podonin.quotes.domain

import android.util.Log
import com.podonin.common_utils.mutableSharedFlow
import com.podonin.quotes.domain.model.Quote
import com.podonin.quotes.domain.repository.QuotesRepository
import com.podonin.quotes.domain.repository.TickersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class QuotesInteractorImpl @Inject constructor(
    private val quotesRepository: QuotesRepository,
    private val tickersRepository: TickersRepository
) : QuotesInteractor {

    private val errorFlow = mutableSharedFlow<String?>()

    override suspend fun subscribeOnQuotes() {
        try {
            val paperList = tickersRepository.getTickers(
                type = STOCKS_TYPE,
                exchange = RUSSIA_EXCHANGE,
                limit = LIMIT,
                gainers = GAINERS
            )
            quotesRepository.subscribeOnQuotes(paperList)
        } catch (e: Exception) {
            Log.e("QuotesInteractorImpl", "Error subscribing on quotes: ${e.message}")
            errorFlow.tryEmit(e.message)
        }
    }

    override fun getQuotesFlow(): Flow<List<Quote>> {
        return quotesRepository.getQuotesFlow()
    }

    override fun getErrorFlow(): Flow<String?> {
        return errorFlow
    }

    companion object {
        private const val STOCKS_TYPE = "stocks"
        private const val RUSSIA_EXCHANGE = "russia"
        private const val LIMIT = 30
        private const val GAINERS = 0
    }
}