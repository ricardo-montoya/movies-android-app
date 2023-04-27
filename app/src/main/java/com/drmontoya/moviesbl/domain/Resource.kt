package com.drmontoya.moviesbl.domain

import com.drmontoya.moviesbl.domain.model.Movie

sealed class Resource(data: List<Movie> = emptyList(), message: String = "") {
    class Success(val data: List<Movie>) : Resource(data)
    class Failed(val message: String) : Resource(message = message)
    object Loading : Resource()
}
