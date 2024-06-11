package com.rpsouza.blogapp.di

import com.rpsouza.blogapp.data.api.ServiceAPI
import com.rpsouza.blogapp.network.ServiceProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun providersServiceProvider() = ServiceProvider()

    @Provides
    @Singleton
    fun providerServiceAPI(
        serviceProvider: ServiceProvider
    ): ServiceAPI {
        return serviceProvider.createService(ServiceAPI::class.java)
    }
}