package com.podonin.quotes.data.repository

import com.podonin.common_io.DispatchersHolder
import com.podonin.quotes.data.datasources.api.QuotesApiDataSource
import com.podonin.quotes.domain.repository.TickersRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TickersRepositoryImpl @Inject constructor(
    private val quotesApiDataSource: QuotesApiDataSource,
    private val dispatchersHolder: DispatchersHolder
) : TickersRepository {

    override suspend fun getTickers(
        type: String,
        exchange: String,
        limit: Int,
        gainers: Int
    ): List<String> {
        return withContext(dispatchersHolder.io) {
            quotesApiDataSource.getTickers(
                cmd = CMD,
                exchange = exchange,
                gainers = gainers,
                limit = limit,
                type = type
            )
        }
    }

    companion object {
        private const val CMD = "getTopSecurities"
    }
}