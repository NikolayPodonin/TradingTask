package com.podonin.quotes.data.api

import com.podonin.quotes.data.api.model.request.TickersBaseRequest
import com.podonin.quotes.data.api.model.response.TickersResponse

interface QuotesApi {
    suspend fun getTickers(request: TickersBaseRequest): TickersResponse
}