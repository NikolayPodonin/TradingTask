package com.podonin.quotes.data.datasources.api

import com.podonin.common_io.http_client.HttpApiClient
import com.podonin.quotes.data.datasources.api.model.request.TickersBaseRequest
import com.podonin.quotes.data.datasources.api.model.request.TickersRequest
import com.podonin.quotes.data.datasources.api.model.request.TickersRequestParams
import com.podonin.quotes.data.datasources.api.model.response.TickersResponse
import io.ktor.client.call.body
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class QuotesApiDataSourceImpl @Inject constructor(
    private val httpApiClient: HttpApiClient
): QuotesApiDataSource {
    override suspend fun getTickers(
        cmd: String,
        exchange: String,
        gainers: Int,
        limit: Int,
        type: String,
    ): List<String> {
        val request = TickersBaseRequest(
            q = TickersRequest(
                cmd = cmd,
                params = TickersRequestParams(
                    exchange = exchange,
                    gainers = gainers,
                    limit = limit,
                    type = type
                )
            )
        )
        return httpApiClient.post {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body<TickersResponse>().tickers
    }
}