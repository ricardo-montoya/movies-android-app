package com.drmontoya.moviesbl.domain.model

import com.drmontoya.moviesbl.data.local.room.movie.MovieEntity

data class Movie(
    val id: Int,
    val backdrop_path: String,
    val original_title: String,
    val overview: String,
    val poster_path: String,
    val release_date: String,
    val vote_average: Double
)

fun Movie.asEntityModel(): MovieEntity {
    return MovieEntity(
        id,
        backdrop_path,
        original_title,
        overview,
        poster_path,
        release_date,
        vote_average
    )
}