package com.drmontoya.moviesbl.data.repository

import android.util.Log
import com.drmontoya.moviesbl.BuildConfig
import com.drmontoya.moviesbl.data.local.room.movie.MovieDao
import com.drmontoya.moviesbl.data.local.room.movie.asDomainModel
import com.drmontoya.moviesbl.data.remote.api.ApiService
import com.drmontoya.moviesbl.data.remote.api.dto.asEntityModel
import com.drmontoya.moviesbl.domain.Resource
import com.drmontoya.moviesbl.domain.model.Movie
import com.drmontoya.moviesbl.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    val movieDao: MovieDao, val apiService: ApiService
) : MovieRepository {

    override suspend fun getSingleMovieById(id: Int): Movie? {
        return movieDao.getMovieById(id)?.asDomainModel()
    }

    override suspend fun getTopRatedMovies(): Flow<Resource> = flow {
        movieDao.getAllTopRatedMovies().onEach {
            emit(Resource.Loading)
            if (!it.isNullOrEmpty()) {
                val list = it.map { it.asDomainModel() }
                emit(Resource.Success(data = list))
            } else {
                emit(Resource.Failed(message = "Comprueba tu conexión a internet"))
            }
        }.collect()
    }

    override suspend fun getPlayingNowMovies(): Flow<Resource> = flow {
        movieDao.getAllNowPlayingMovies().onEach {
            emit(Resource.Loading)
            if (!it.isNullOrEmpty()) {
                val list = it.map { it.asDomainModel() }
                emit(Resource.Success(data = list))
            } else {
                emit(Resource.Failed(message = "Comprueba tu conexión a internet"))
            }
        }.collect()
    }

    override suspend fun upsertTopRatedMovies() {
        try {
            val response = apiService.getTopRatedMovies(BuildConfig.TMDB_API_KEY)
            response.body()?.results?.forEach { movie ->
                val updatedMovie = movieDao.getMovieById(movie.id)
                if (updatedMovie != null) {
                    updatedMovie.top_rated = 1
                    movieDao.insertSingleMovie(updatedMovie)
                } else {
                    val movieToInsert = movie.asEntityModel()
                    movieToInsert.top_rated = 1
                    movieDao.insertSingleMovie(movieToInsert)
                }
            }

        } catch (e: Exception) {
            Log.e("Retrofit:", e.message ?: "Unexpected Error")
        }

    }

    override suspend fun upsertNowPlayingMovies() {
        try {
            val response = apiService.getNowPlayingMovies(BuildConfig.TMDB_API_KEY)
            response.body()?.results?.forEach { movie ->
                val updatedMovie = movieDao.getMovieById(movie.id)
                if (updatedMovie != null) {
                    updatedMovie.now_playing = 1
                    movieDao.insertSingleMovie(updatedMovie)
                } else {
                    val movieToInsert = movie.asEntityModel()
                    movieToInsert.now_playing = 1
                    movieDao.insertSingleMovie(movieToInsert)
                }
            }

        } catch (e : Exception) {
            Log.e("Retrofit:", e.message ?: "Unexpected Error")
        }

    }

}