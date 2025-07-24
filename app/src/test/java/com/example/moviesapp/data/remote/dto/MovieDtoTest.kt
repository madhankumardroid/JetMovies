package com.example.moviesapp.data.remote.dto

import com.example.moviesapp.domain.model.Movie
import com.example.moviesapp.domain.model.MovieDetail
import com.example.moviesapp.domain.model.Rating
import com.example.moviesapp.domain.model.SearchResponse
import org.junit.Assert.assertEquals
import org.junit.Test

class MovieDtoTest {

    @Test
    fun `toMovie maps correctly`() {
        // Given
        val movieDto = MovieDto(
            imdbID = "tt0096895",
            Title = "Batman",
            Year = "1989",
            Poster = "https://example.com/batman.jpg",
            Type = "movie"
        )

        // When
        val movie = movieDto.toMovie()

        // Then
        assertEquals("tt0096895", movie.imdbID)
        assertEquals("Batman", movie.title)
        assertEquals("1989", movie.year)
        assertEquals("https://example.com/batman.jpg", movie.poster)
        assertEquals("movie", movie.type)
    }

    @Test
    fun `toMovieDetail maps correctly`() {
        // Given
        val ratingDto = RatingDto(
            Source = "Internet Movie Database",
            Value = "7.5/10"
        )
        val movieDetailDto = MovieDetailDto(
            imdbID = "tt0096895",
            Title = "Batman",
            Year = "1989",
            Rated = "PG-13",
            Released = "23 Jun 1989",
            Runtime = "126 min",
            Genre = "Action, Adventure",
            Director = "Tim Burton",
            Writer = "Bob Kane, Sam Hamm",
            Actors = "Michael Keaton, Jack Nicholson, Kim Basinger",
            Plot = "The Dark Knight of Gotham City begins his war on crime...",
            Poster = "https://example.com/batman.jpg",
            Ratings = listOf(ratingDto),
            imdbRating = "7.5",
            imdbVotes = "350,000",
            Type = "movie",
            DVD = "25 Sep 2001",
            BoxOffice = "$251,188,924",
            Production = "Warner Bros.",
            Website = "N/A",
            Response = "True"
        )

        // When
        val movieDetail = movieDetailDto.toMovieDetail()

        // Then
        assertEquals("tt0096895", movieDetail.imdbID)
        assertEquals("Batman", movieDetail.title)
        assertEquals("1989", movieDetail.year)
        assertEquals("PG-13", movieDetail.rated)
        assertEquals("23 Jun 1989", movieDetail.released)
        assertEquals("126 min", movieDetail.runtime)
        assertEquals("Action, Adventure", movieDetail.genre)
        assertEquals("Tim Burton", movieDetail.director)
        assertEquals("Bob Kane, Sam Hamm", movieDetail.writer)
        assertEquals("Michael Keaton, Jack Nicholson, Kim Basinger", movieDetail.actors)
        assertEquals("The Dark Knight of Gotham City begins his war on crime...", movieDetail.plot)
        assertEquals("https://example.com/batman.jpg", movieDetail.poster)
        assertEquals("7.5", movieDetail.imdbRating)
        assertEquals("350,000", movieDetail.imdbVotes)
        assertEquals("movie", movieDetail.type)
        assertEquals("25 Sep 2001", movieDetail.dvd)
        assertEquals("$251,188,924", movieDetail.boxOffice)
        assertEquals("Warner Bros.", movieDetail.production)
        assertEquals("N/A", movieDetail.website)
        assertEquals(1, movieDetail.ratings.size)
        assertEquals("Internet Movie Database", movieDetail.ratings[0].source)
        assertEquals("7.5/10", movieDetail.ratings[0].value)
    }

    @Test
    fun `toRating maps correctly`() {
        // Given
        val ratingDto = RatingDto(
            Source = "Rotten Tomatoes",
            Value = "72%"
        )

        // When
        val rating = ratingDto.toRating()

        // Then
        assertEquals("Rotten Tomatoes", rating.source)
        assertEquals("72%", rating.value)
    }

    @Test
    fun `toSearchResponse maps correctly`() {
        // Given
        val movieDto = MovieDto(
            imdbID = "tt0096895",
            Title = "Batman",
            Year = "1989",
            Poster = "https://example.com/batman.jpg",
            Type = "movie"
        )
        val searchResponseDto = SearchResponseDto(
            Search = listOf(movieDto),
            totalResults = "1",
            Response = "True"
        )

        // When
        val searchResponse = searchResponseDto.toSearchResponse()

        // Then
        assertEquals(1, searchResponse.search?.size)
        assertEquals("tt0096895", searchResponse.search?.get(0)?.imdbID)
        assertEquals("Batman", searchResponse.search?.get(0)?.title)
        assertEquals("1", searchResponse.totalResults)
        assertEquals("True", searchResponse.response)
    }

    @Test
    fun `toSearchResponse handles null search results`() {
        // Given
        val searchResponseDto = SearchResponseDto(
            Search = null,
            totalResults = null,
            Response = "False"
        )

        // When
        val searchResponse = searchResponseDto.toSearchResponse()

        // Then
        assertEquals(null, searchResponse.search)
        assertEquals(null, searchResponse.totalResults)
        assertEquals("False", searchResponse.response)
    }
} 