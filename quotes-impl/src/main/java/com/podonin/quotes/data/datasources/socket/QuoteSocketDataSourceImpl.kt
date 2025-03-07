package com.podonin.quotes.data.datasources.socket

import com.podonin.quotes.data.datasources.socket.client.QuoteSocketClient
import com.podonin.quotes.data.mapper.mapToRequest
import javax.inject.Inject

class QuoteSocketDataSourceImpl @Inject constructor(
    private val webSocketClient: QuoteSocketClient,
) : QuoteSocketDataSource {

    override val quotesFlow = webSocketClient.resultFlow

    override suspend fun launchQuotesWS(papers: List<String>) {
        val subscribeRequest = (REQUEST_EVENT to papers).mapToRequest()
        webSocketClient.connect(subscribeRequest)
    }

    companion object {
        private const val REQUEST_EVENT = "realtimeQuotes"
    }
}