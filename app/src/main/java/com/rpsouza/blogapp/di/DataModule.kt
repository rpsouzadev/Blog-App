package com.rpsouza.blogapp.di

import android.content.Context
import com.rpsouza.blogapp.data.store.SharedPreferenceHelper
import com.rpsouza.blogapp.utils.NetworkHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun providerSharedPreferenceHelper(@ApplicationContext context: Context) = SharedPreferenceHelper(context)

    @Provides
    @Singleton
    fun provideRNetworkHelper(@ApplicationContext context: Context) = NetworkHelper(context)
}