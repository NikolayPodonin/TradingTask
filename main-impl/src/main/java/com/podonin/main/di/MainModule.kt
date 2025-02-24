package com.podonin.main.di

import com.podonin.main.navigation.MainNavigator
import com.podonin.main.navigation.MainNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MainModule {
    @Binds
    @Singleton
    abstract fun getMainNavigator(impl: MainNavigatorImpl): MainNavigator
}