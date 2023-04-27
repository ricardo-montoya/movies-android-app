package com.drmontoya.moviesbl.di.module

import com.drmontoya.moviesbl.data.repository.MovieRepositoryImpl
import com.drmontoya.moviesbl.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideMovieRepository(repo: MovieRepositoryImpl): MovieRepository

}