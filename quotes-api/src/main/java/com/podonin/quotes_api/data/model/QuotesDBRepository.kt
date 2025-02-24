package com.podonin.quotes_api.data.model

import kotlinx.coroutines.flow.Flow

interface QuotesDBRepository {
    fun getQuotesFlow(): Flow<List<Quote>>
}