package com.drmontoya.moviesbl.domain.repository

import com.drmontoya.moviesbl.domain.Resource
import com.drmontoya.moviesbl.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getSingleMovieById(id: Int): Movie?
    suspend fun getTopRatedMovies(): Flow<Resource>
    suspend fun getPlayingNowMovies(): Flow<Resource>
    suspend fun upsertTopRatedMovies()
    suspend fun upsertNowPlayingMovies()
}