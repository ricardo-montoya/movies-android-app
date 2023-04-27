package com.drmontoya.moviesbl.data.local.room.movie

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies WHERE top_rated = 1")
    fun getAllTopRatedMovies(): Flow<List<MovieEntity>?>

    @Query("SELECT * FROM movies WHERE now_playing = 1")
    fun getAllNowPlayingMovies(): Flow<List<MovieEntity>?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(vararg movies: MovieEntity)

    @Query("SELECT * FROM movies WHERE id = :id")
    suspend fun getMovieById(id: Int): MovieEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSingleMovie(movieEntity: MovieEntity)
}