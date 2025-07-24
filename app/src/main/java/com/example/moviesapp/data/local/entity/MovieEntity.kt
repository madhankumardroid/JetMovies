package com.example.moviesapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey
    val imdbID: String,
    val title: String,
    val year: String,
    val poster: String,
    val type: String
)

@Entity(tableName = "movie_details")
data class MovieDetailEntity(
    @PrimaryKey
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
    val imdbRating: String,
    val imdbVotes: String,
    val type: String,
    val dvd: String,
    val boxOffice: String,
    val production: String,
    val website: String
) 