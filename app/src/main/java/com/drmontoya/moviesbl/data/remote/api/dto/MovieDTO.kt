package com.drmontoya.moviesbl.data.remote.api.dto

import com.drmontoya.moviesbl.data.local.room.movie.MovieEntity
import com.drmontoya.moviesbl.domain.model.Movie

data class MovieDTO(
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)

fun MovieDTO.asDomainModel(): Movie {
    return Movie(
        id,
        backdrop_path,
        original_title,
        overview,
        poster_path,
        release_date,
        vote_average
    )
}

fun MovieDTO.asEntityModel(): MovieEntity {
    return MovieEntity(
        id,
        backdrop_path,
        original_title,
        overview,
        poster_path,
        release_date,
        vote_average,
        0,
        0
    )
}