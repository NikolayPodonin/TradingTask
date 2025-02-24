package com.podonin.quotes.data.repository

import android.util.Log
import com.podonin.common_io.DispatchersHolder
import com.podonin.quotes.data.api.QuotesApi
import com.podonin.quotes.data.api.model.request.TickersBaseRequest
import com.podonin.quotes.data.api.model.request.TickersRequest
import com.podonin.quotes.data.api.model.request.TickersRequestParams
import com.podonin.quotes.domain.repository.TickersRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TickersRepositoryImpl @Inject constructor(
    private val quotesApi: QuotesApi,
    private val dispatchersHolder: DispatchersHolder
) : TickersRepository {

    override suspend fun getTickers(
        type: String,
        exchange: String,
        limit: Int,
        gainers: Int
    ): List<String> {
        return withContext(dispatchersHolder.io) {
            try {
                quotesApi.getTickers(
                    TickersBaseRequest(
                        q = TickersRequest(
                            cmd = CMD,
                            params = TickersRequestParams(
                                exchange = exchange,
                                gainers = gainers,
                                limit = limit,
                                type = type
                            )
                        )
                    )
                ).tickers
            } catch (e: Exception) {
                Log.e("TickersRepository", e.message, e)
                emptyList()
            }
        }
    }

    companion object {
        private const val CMD = "getTopSecurities"
    }
}