package com.podonin.quotes.di

import android.content.Context
import androidx.room.Room
import com.podonin.quotes.data.api.QuotesApi
import com.podonin.quotes.data.api.QuotesApiImpl
import com.podonin.quotes.data.repository.QuotesDBRepositoryImpl
import com.podonin.quotes.data.repository.QuotesRepositoryImpl
import com.podonin.quotes.data.database.QuoteDatabase
import com.podonin.quotes.data.database.dao.QuoteDao
import com.podonin.quotes.data.repository.TickersRepositoryImpl
import com.podonin.quotes.domain.QuotesInteractor
import com.podonin.quotes.domain.QuotesInteractorImpl
import com.podonin.quotes.domain.repository.QuotesRepository
import com.podonin.quotes.domain.repository.TickersRepository
import com.podonin.quotes.navigation.QuotesEntryPointImpl
import com.podonin.quotes_api.data.model.QuotesDBRepository
import com.podonin.quotes_api.navigation.QuotesEntryPoint
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class QuotesModule {
    @Binds
    @Singleton
    abstract fun bindEntryPoint(impl: QuotesEntryPointImpl): QuotesEntryPoint

    @Binds
    @Singleton
    abstract fun bindQuotesApi(impl: QuotesApiImpl): QuotesApi

    @Binds
    @Singleton
    abstract fun bindQuotesRepository(impl: QuotesRepositoryImpl): QuotesRepository

    @Binds
    @Singleton
    abstract fun bindTickersRepository(impl: TickersRepositoryImpl): TickersRepository

    @Binds
    @Singleton
    abstract fun bindQuotesDBRepository(impl: QuotesDBRepositoryImpl): QuotesDBRepository

    @Binds
    abstract fun bindQuotesInteractor(impl: QuotesInteractorImpl): QuotesInteractor

    companion object {
        @Provides
        @Singleton
        fun provideDatabase(@ApplicationContext context: Context): QuoteDatabase {
            val clazz = QuoteDatabase::class.java
            return Room.databaseBuilder(context, clazz, clazz.name).build()
        }

        @Provides
        @Singleton
        fun provideQuoteDao(db: QuoteDatabase): QuoteDao = db.quoteDao()
    }
}