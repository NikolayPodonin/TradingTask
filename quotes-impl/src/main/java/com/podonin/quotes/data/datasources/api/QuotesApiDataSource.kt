package com.podonin.quotes.data.datasources.api

interface QuotesApiDataSource {
    suspend fun getTickers(
        cmd: String,
        exchange: String,
        gainers: Int,
        limit: Int,
        type: String
    ): List<String>
}