package com.example.moviesapp.data.local

import androidx.room.*
import com.example.moviesapp.data.local.entity.MovieEntity
import com.example.moviesapp.data.local.entity.MovieDetailEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies")
    fun getAllMovies(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieDetail(movieDetail: MovieDetailEntity)

    @Query("SELECT * FROM movie_details WHERE imdbID = :imdbId")
    suspend fun getMovieDetail(imdbId: String): MovieDetailEntity?

    @Query("DELETE FROM movies")
    suspend fun deleteAllMovies()

    @Query("DELETE FROM movie_details")
    suspend fun deleteAllMovieDetails()
} 