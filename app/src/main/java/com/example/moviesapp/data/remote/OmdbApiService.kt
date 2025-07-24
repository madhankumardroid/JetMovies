package com.example.moviesapp.data.remote

import com.example.moviesapp.BuildConfig
import com.example.moviesapp.data.remote.dto.MovieDetailDto
import com.example.moviesapp.data.remote.dto.SearchResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbApiService {
    @GET("/")
    suspend fun searchMovies(
        @Query("s") query: String,
        @Query("apikey") apiKey: String = BuildConfig.OMDB_API_KEY
    ): SearchResponseDto

    @GET("/")
    suspend fun getMovieDetail(
        @Query("i") imdbId: String,
        @Query("apikey") apiKey: String = BuildConfig.OMDB_API_KEY
    ): MovieDetailDto
} 