package com.drmontoya.moviesbl.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.drmontoya.moviesbl.data.local.room.movie.MovieDao
import com.drmontoya.moviesbl.data.local.room.movie.MovieEntity


@Database(
    entities = [MovieEntity::class],
    version = 1,
    exportSchema = false
)
abstract class Database : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}