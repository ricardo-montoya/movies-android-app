package com.drmontoya.moviesbl.data.remote.api

import com.drmontoya.moviesbl.data.remote.api.dto.NowPlayingContainerDTO
import com.drmontoya.moviesbl.data.remote.api.dto.TopRatedContainerDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(ApiConstants.TOP_RATED_END_POINT)
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String
    ): Response<TopRatedContainerDTO>

    @GET(ApiConstants.NOW_PLAYING_END_POINT)
    suspend fun getNowPlayingMovies(
        @Query("api_key") apiKey: String
    ): Response<NowPlayingContainerDTO>

}