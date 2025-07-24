package com.example.moviesapp.data.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.example.moviesapp.data.local.entity.MovieEntity
import com.example.moviesapp.data.local.entity.MovieDetailEntity

@Database(
    entities = [MovieEntity::class, MovieDetailEntity::class],
    version = 1
)
abstract class MovieDatabase : RoomDatabase() {
    abstract val dao: MovieDao

    companion object {
        @Volatile
        private var INSTANCE: MovieDatabase? = null

        fun getInstance(context: Context): MovieDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java,
                    "movie_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
} 