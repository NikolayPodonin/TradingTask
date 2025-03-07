package com.podonin.common_io.di

import com.podonin.common_io.BuildConfig
import com.podonin.common_io.DispatchersHolder
import com.podonin.common_io.DispatchersHolderImpl
import com.podonin.common_io.http_client.HttpApiClient
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class IoModule {

    @Binds
    abstract fun getDispatchersHolder(impl: DispatchersHolderImpl): DispatchersHolder

    companion object {

        @Provides
        @Singleton
        fun provideHttpClient(): HttpApiClient {
            return HttpApiClient(BuildConfig.HTTP_API_URL)
        }
    }
}
