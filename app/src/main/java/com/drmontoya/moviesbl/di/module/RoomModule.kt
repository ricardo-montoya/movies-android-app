package com.drmontoya.moviesbl.di.module

import android.content.Context
import androidx.room.Room
import com.drmontoya.moviesbl.data.local.room.Database
import com.drmontoya.moviesbl.data.local.room.movie.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {
    @Provides
    @Singleton
    fun providesDataBase(@ApplicationContext context: Context): Database {
        return Room
            .databaseBuilder(context, Database::class.java, "database")
            .build()
    }

    @Provides
    @Singleton
    fun providesMovieDao(database: Database): MovieDao {
        return database.movieDao()
    }

}