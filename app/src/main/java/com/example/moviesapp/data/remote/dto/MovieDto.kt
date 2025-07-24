package com.example.moviesapp.data.remote.dto

import com.example.moviesapp.domain.model.Movie
import com.example.moviesapp.domain.model.MovieDetail
import com.example.moviesapp.domain.model.Rating
import com.example.moviesapp.domain.model.SearchResponse

data class MovieDto(
    val imdbID: String,
    val Title: String,
    val Year: String,
    val Poster: String,
    val Type: String
) {
    fun toMovie(): Movie {
        return Movie(
            imdbID = imdbID,
            title = Title,
            year = Year,
            poster = Poster,
            type = Type
        )
    }
}

data class MovieDetailDto(
    val imdbID: String,
    val Title: String,
    val Year: String,
    val Rated: String,
    val Released: String,
    val Runtime: String,
    val Genre: String,
    val Director: String,
    val Writer: String,
    val Actors: String,
    val Plot: String,
    val Poster: String,
    val Ratings: List<RatingDto>?,
    val imdbRating: String,
    val imdbVotes: String,
    val Type: String,
    val DVD: String,
    val BoxOffice: String,
    val Production: String,
    val Website: String,
    val Response: String
) {
    fun toMovieDetail(): MovieDetail {
        return MovieDetail(
            imdbID = imdbID,
            title = Title,
            year = Year,
            rated = Rated,
            released = Released,
            runtime = Runtime,
            genre = Genre,
            director = Director,
            writer = Writer,
            actors = Actors,
            plot = Plot,
            poster = Poster,
            ratings = Ratings?.map { it.toRating() } ?: emptyList(),
            imdbRating = imdbRating,
            imdbVotes = imdbVotes,
            type = Type,
            dvd = DVD,
            boxOffice = BoxOffice,
            production = Production,
            website = Website
        )
    }
}

data class RatingDto(
    val Source: String,
    val Value: String
) {
    fun toRating(): Rating {
        return Rating(
            source = Source,
            value = Value
        )
    }
}

data class SearchResponseDto(
    val Search: List<MovieDto>?,
    val totalResults: String?,
    val Response: String
) {
    fun toSearchResponse(): SearchResponse {
        return SearchResponse(
            search = Search?.map { it.toMovie() },
            totalResults = totalResults,
            response = Response
        )
    }
} 