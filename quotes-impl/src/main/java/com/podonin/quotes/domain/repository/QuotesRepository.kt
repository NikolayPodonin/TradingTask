package com.podonin.quotes.domain.repository

interface QuotesRepository {
    fun subscribeOnQuotes(paperList: List<String>)
}