package com.podonin.quotes.domain.repository

interface TickersRepository {
    suspend fun getTickers(
        type: String,
        exchange: String,
        limit: Int,
        gainers: Int
    ): List<String>
}