package com.eminesa.beinconnectclone.di

import com.eminesa.beinconnectclone.data.repository.GenreRepositoryImpl
import com.eminesa.beinconnectclone.data.repository.MovieRepositoryImpl
import com.eminesa.beinconnectclone.domain.repository.GenreRepository
import com.eminesa.beinconnectclone.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun repositoryBinds(repositoryImpl: MovieRepositoryImpl) : MovieRepository

    @Binds
    @Singleton
    fun genreRepositoryBinds(repositoryImpl: GenreRepositoryImpl) : GenreRepository

}