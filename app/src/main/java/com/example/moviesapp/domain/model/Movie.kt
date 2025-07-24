package com.example.moviesapp.domain.model

data class Movie(
    val imdbID: String,
    val title: String,
    val year: String,
    val poster: String,
    val type: String
)

data class MovieDetail(
    val imdbID: String,
    val title: String,
    val year: String,
    val rated: String,
    val released: String,
    val runtime: String,
    val genre: String,
    val director: String,
    val writer: String,
    val actors: String,
    val plot: String,
    val poster: String,
    val ratings: List<Rating>,
    val imdbRating: String,
    val imdbVotes: String,
    val type: String,
    val dvd: String,
    val boxOffice: String,
    val production: String,
    val website: String
)

data class Rating(
    val source: String,
    val value: String
)

data class SearchResponse(
    val search: List<Movie>?,
    val totalResults: String?,
    val response: String
) 