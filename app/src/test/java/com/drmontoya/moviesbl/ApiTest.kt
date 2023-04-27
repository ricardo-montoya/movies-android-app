package com.drmontoya.moviesbl

import com.drmontoya.moviesbl.data.remote.api.ApiConstants
import com.drmontoya.moviesbl.data.remote.api.ApiService
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiTest {
    private lateinit var apiService: ApiService
    private lateinit var apiKey: String

    @Before
    fun initializeApiService() {
        apiService = Retrofit.Builder()
            .baseUrl(ApiConstants.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
        apiKey = BuildConfig.TMDB_API_KEY
    }

    @Test
    fun returnATopRatedResponseWithTheObjectsCorrectlyParsed() {
        val response = runBlocking {
            apiService.getTopRatedMovies(apiKey = apiKey)
        }
        assertTrue(response.isSuccessful)
        assertNotNull(response.body())
        assertNotNull(response.body()?.results)
        assertTrue(response.body()?.results?.isNotEmpty() ?: false)
    }

    @Test
    fun returnANowPlayingResponseWithTheObjectsCorrectlyParsed() {
        val response = runBlocking {
            apiService.getNowPlayingMovies(apiKey = apiKey)
        }
        assertTrue(response.isSuccessful)
        assertNotNull(response.body())
        assertNotNull(response.body()?.results)
        assertTrue(response.body()?.results?.isNotEmpty() ?: false)
    }
}