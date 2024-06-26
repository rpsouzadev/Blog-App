package com.rpsouza.blogapp.di

import android.content.Context
import com.rpsouza.blogapp.data.repository.PostRepositoryImpl
import com.rpsouza.blogapp.domain.repository.PostRepository
import com.rpsouza.blogapp.utils.NetworkHelper
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {

    @Binds
    abstract fun bindsPostRepositoryImpl(
        postRepositoryImpl: PostRepositoryImpl
    ): PostRepository
}