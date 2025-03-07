package com.podonin.quotes.di

import android.content.Context
import androidx.room.Room
import com.podonin.common_io.BuildConfig
import com.podonin.common_io.DispatchersHolder
import com.podonin.common_io.web_sockets.SocketMessageMapper
import com.podonin.quotes.data.datasources.api.QuotesApiDataSource
import com.podonin.quotes.data.datasources.api.QuotesApiDataSourceImpl
import com.podonin.quotes.data.datasources.database.QuoteDatabase
import com.podonin.quotes.data.datasources.database.QuotesDBDataSource
import com.podonin.quotes.data.datasources.database.QuotesDBDataSourceImpl
import com.podonin.quotes.data.datasources.database.dao.QuoteDao
import com.podonin.quotes.data.datasources.socket.QuoteSocketDataSource
import com.podonin.quotes.data.datasources.socket.QuoteSocketDataSourceImpl
import com.podonin.quotes.data.datasources.socket.client.QuoteSocketClient
import com.podonin.quotes.data.mapper.SocketResponseMapper
import com.podonin.quotes.data.model.QuoteEntity
import com.podonin.quotes.data.repository.QuotesRepositoryImpl
import com.podonin.quotes.data.repository.TickersRepositoryImpl
import com.podonin.quotes.domain.QuotesInteractor
import com.podonin.quotes.domain.QuotesInteractorImpl
import com.podonin.quotes.domain.repository.QuotesRepository
import com.podonin.quotes.domain.repository.TickersRepository
import com.podonin.quotes.navigation.QuotesEntryPointImpl
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
    abstract fun quotesApiDataSource(impl: QuotesApiDataSourceImpl): QuotesApiDataSource

    @Binds
    @Singleton
    abstract fun quotesDBDataSource(impl: QuotesDBDataSourceImpl): QuotesDBDataSource

    @Binds
    @Singleton
    abstract fun quotesSocketDataSource(impl: QuoteSocketDataSourceImpl): QuoteSocketDataSource

    @Binds
    @Singleton
    abstract fun quotesSocketMessageMapper(impl: SocketResponseMapper): SocketMessageMapper<QuoteEntity>

    @Binds
    @Singleton
    abstract fun bindQuotesRepository(impl: QuotesRepositoryImpl): QuotesRepository

    @Binds
    @Singleton
    abstract fun bindTickersRepository(impl: TickersRepositoryImpl): TickersRepository

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

        @Provides
        @Singleton
        fun provideQuoteSocketClient(
            messageMapper: SocketMessageMapper<QuoteEntity>,
            dispatchersHolder: DispatchersHolder
        ): QuoteSocketClient {
            return QuoteSocketClient(
                url = BuildConfig.WEB_SOCKET_URL,
                messageMapper = messageMapper,
                dispatchersHolder = dispatchersHolder
            )
        }
    }
}