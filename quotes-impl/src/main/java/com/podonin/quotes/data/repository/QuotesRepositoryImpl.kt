package com.podonin.quotes.data.repository

import android.util.Log
import com.podonin.common_io.DispatchersHolder
import com.podonin.common_io.web_sockets.DefaultSocketListener
import com.podonin.common_io.web_sockets.WebSocketClient
import com.podonin.common_utils.mutableSharedFlow
import com.podonin.quotes.data.database.dao.QuoteDao
import com.podonin.quotes.data.mapper.mapToRequest
import com.podonin.quotes.data.mapper.toEntity
import com.podonin.quotes.domain.repository.QuotesRepository
import com.podonin.quotes_api.data.model.Quote
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.decodeFromJsonElement
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuotesRepositoryImpl @Inject constructor(
    dispatchersHolder: DispatchersHolder,
    private val webSocketClient: WebSocketClient,
    private val quoteDao: QuoteDao,
    private val quoteSocketListener: QuoteSocketListener
) : QuotesRepository {

    private val ioScope = CoroutineScope(dispatchersHolder.io)

    override fun subscribeOnQuotes(paperList: List<String>) {
        ioScope.launch {
            val subscribeRequest = (REQUEST_EVENT to paperList).mapToRequest()
            launch {
                quoteSocketListener.quoteFlow
                    .collect { quote ->
                        launch {
                            val existed = quoteDao.getQuote(quote.ticker)
                            if (existed == null) {
                                quoteDao.insert(quote.toEntity())
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
                    }
            }
            launch {
                quoteSocketListener.disconnectFlow.collect {
                    webSocketClient.close()
                }
            }

            webSocketClient.connect(subscribeRequest, quoteSocketListener)
        }
    }

    companion object {
        private const val REQUEST_EVENT = "realtimeQuotes"
        private const val SOCKET_DEBOUNCE_TIME = 500L
    }
}