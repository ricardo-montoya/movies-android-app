package com.drmontoya.moviesbl.data.local.room.movie

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.drmontoya.moviesbl.domain.model.Movie

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey
    val id: Int,
    val backdrop_path: String,
    val original_title: String,
    val overview: String,
    val poster_path: String,
    val release_date: String,
    val vote_average: Double,
    var top_rated : Int = 0,
    var now_playing : Int = 0
)


fun MovieEntity.asDomainModel(): Movie {
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