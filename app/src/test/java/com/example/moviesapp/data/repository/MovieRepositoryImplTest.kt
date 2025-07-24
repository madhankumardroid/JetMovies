package com.example.moviesapp.data.repository

import com.example.moviesapp.data.local.MovieDao
import com.example.moviesapp.data.local.entity.MovieEntity
import com.example.moviesapp.data.local.entity.MovieDetailEntity
import com.example.moviesapp.data.remote.OmdbApiService
import com.example.moviesapp.data.remote.dto.MovieDto
import com.example.moviesapp.data.remote.dto.MovieDetailDto
import com.example.moviesapp.data.remote.dto.SearchResponseDto
import com.example.moviesapp.domain.model.Movie
import com.example.moviesapp.domain.model.MovieDetail
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class MovieRepositoryImplTest {

    private lateinit var movieRepository: MovieRepositoryImpl
    private lateinit var mockApi: OmdbApiService
    private lateinit var mockDao: MovieDao

    @Before
    fun setUp() {
        mockApi = mockk()
        mockDao = mockk()
        movieRepository = MovieRepositoryImpl(mockApi, mockDao)
    }

    @Test
    fun `searchMovies with valid response returns movies`() = runTest {
        // Given
        val query = "Batman"
        val movieDto = MovieDto(
            imdbID = "tt0096895",
            Title = "Batman",
            Year = "1989",
            Poster = "https://example.com/batman.jpg",
            Type = "movie"
        )
        val searchResponse = SearchResponseDto(
            Search = listOf(movieDto),
            totalResults = "1",
            Response = "True"
        )
        coEvery { mockApi.searchMovies(query) } returns searchResponse
        coEvery { mockDao.insertMovies(any()) } returns Unit

        // When
        val result = movieRepository.searchMovies(query)

        // Then
        assertTrue(result.isSuccess)
        val movies = result.getOrNull()!!
        assertEquals(1, movies.size)
        assertEquals("tt0096895", movies[0].imdbID)
        assertEquals("Batman", movies[0].title)
        coVerify { mockDao.insertMovies(any()) }
    }

    @Test
    fun `searchMovies with false response returns failure`() = runTest {
        // Given
        val query = "Invalid"
        val searchResponse = SearchResponseDto(
            Search = null,
            totalResults = null,
            Response = "False"
        )
        coEvery { mockApi.searchMovies(query) } returns searchResponse

        // When
        val result = movieRepository.searchMovies(query)

        // Then
        assertTrue(result.isFailure)
        assertEquals("No movies found", result.exceptionOrNull()?.message)
    }

    @Test
    fun `getMovieDetail with valid response returns movie detail`() = runTest {
        // Given
        val imdbId = "tt0096895"
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
            Ratings = emptyList(),
            imdbRating = "7.5",
            imdbVotes = "350,000",
            Type = "movie",
            DVD = "25 Sep 2001",
            BoxOffice = "$251,188,924",
            Production = "Warner Bros.",
            Website = "N/A",
            Response = "True"
        )
        coEvery { mockApi.getMovieDetail(imdbId) } returns movieDetailDto
        coEvery { mockDao.insertMovieDetail(any()) } returns Unit

        // When
        val result = movieRepository.getMovieDetail(imdbId)

        // Then
        assertTrue(result.isSuccess)
        val movieDetail = result.getOrNull()!!
        assertEquals("tt0096895", movieDetail.imdbID)
        assertEquals("Batman", movieDetail.title)
        coVerify { mockDao.insertMovieDetail(any()) }
    }

    @Test
    fun `getMovieDetail with false response returns failure`() = runTest {
        // Given
        val imdbId = "invalid_id"
        val movieDetailDto = MovieDetailDto(
            imdbID = "",
            Title = "",
            Year = "",
            Rated = "",
            Released = "",
            Runtime = "",
            Genre = "",
            Director = "",
            Writer = "",
            Actors = "",
            Plot = "",
            Poster = "",
            Ratings = emptyList(),
            imdbRating = "",
            imdbVotes = "",
            Type = "",
            DVD = "",
            BoxOffice = "",
            Production = "",
            Website = "",
            Response = "False"
        )
        coEvery { mockApi.getMovieDetail(imdbId) } returns movieDetailDto

        // When
        val result = movieRepository.getMovieDetail(imdbId)

        // Then
        assertTrue(result.isFailure)
        assertEquals("Movie not found", result.exceptionOrNull()?.message)
    }

    @Test
    fun `getSavedMovies returns movies from dao`() = runTest {
        // Given
        val movieEntity = MovieEntity(
            imdbID = "tt0096895",
            title = "Batman",
            year = "1989",
            poster = "https://example.com/batman.jpg",
            type = "movie"
        )
        coEvery { mockDao.getAllMovies() } returns flowOf(listOf(movieEntity))

        // When
        val result = movieRepository.getSavedMovies()

        // Then
        result.collect { movies ->
            assertEquals(1, movies.size)
            assertEquals("tt0096895", movies[0].imdbID)
            assertEquals("Batman", movies[0].title)
        }
    }
} 