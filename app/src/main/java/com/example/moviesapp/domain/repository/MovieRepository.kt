package com.example.moviesapp.domain.repository

import com.example.moviesapp.domain.model.Movie
import com.example.moviesapp.domain.model.MovieDetail
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun searchMovies(query: String): Result<List<Movie>>
    suspend fun getMovieDetail(imdbId: String): Result<MovieDetail>
    suspend fun saveMovies(movies: List<Movie>)
    suspend fun getSavedMovies(): Flow<List<Movie>>
    suspend fun saveMovieDetail(movieDetail: MovieDetail)
    suspend fun getSavedMovieDetail(imdbId: String): MovieDetail?
}