package com.podonin.quotes.data.api

import com.podonin.common_io.http_client.HttpApiClient
import com.podonin.quotes.data.api.model.request.TickersBaseRequest
import com.podonin.quotes.data.api.model.response.TickersResponse
import io.ktor.client.call.body
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class QuotesApiImpl @Inject constructor(
    private val httpApiClient: HttpApiClient
): QuotesApi {
    override suspend fun getTickers(request: TickersBaseRequest): TickersResponse {
        return httpApiClient.post {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }
}