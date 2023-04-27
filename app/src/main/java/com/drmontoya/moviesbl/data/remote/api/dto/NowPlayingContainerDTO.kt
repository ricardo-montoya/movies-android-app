package com.drmontoya.moviesbl.data.remote.api.dto

data class NowPlayingContainerDTO(
    val dates: DatesDTO,
    val page: Int,
    val results: List<MovieDTO>,
    val total_pages: Int,
    val total_results: Int
)
