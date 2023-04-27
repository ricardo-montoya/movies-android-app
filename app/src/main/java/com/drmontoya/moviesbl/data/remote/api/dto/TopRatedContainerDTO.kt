package com.drmontoya.moviesbl.data.remote.api.dto

data class TopRatedContainerDTO(
    val page: Int,
    val results: List<MovieDTO>,
    val total_pages: Int,
    val total_results: Int
)
