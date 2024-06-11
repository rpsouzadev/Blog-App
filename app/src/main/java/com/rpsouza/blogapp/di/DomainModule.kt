package com.rpsouza.blogapp.di

import com.rpsouza.blogapp.data.repository.PostRepositoryImpl
import com.rpsouza.blogapp.domain.repository.PostRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {

    @Binds
    abstract fun bindsPostRepositoryImpl(
        postRepositoryImpl: PostRepositoryImpl
    ): PostRepository
}