package com.drmontoya.moviesbl.domain.use_case

import com.drmontoya.moviesbl.data.local.room.movie.MovieDao
import com.drmontoya.moviesbl.data.local.room.movie.asDomainModel
import com.drmontoya.moviesbl.domain.model.Movie
import javax.inject.Inject

class GetMovieByIdUseCase
@Inject constructor(
    val movieDao: MovieDao
) {
    suspend operator fun invoke(id: Int): Movie? {
        return movieDao.getMovieById(id)?.asDomainModel()
    }
}