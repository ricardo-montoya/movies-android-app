package com.drmontoya.moviesbl.di.module

import com.drmontoya.moviesbl.data.remote.api.ApiConstants
import com.drmontoya.moviesbl.data.remote.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    @Provides
    @Singleton
    fun providesApiInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesApiService(apiInstance: Retrofit): ApiService {
        return apiInstance.create(ApiService::class.java)
    }
}