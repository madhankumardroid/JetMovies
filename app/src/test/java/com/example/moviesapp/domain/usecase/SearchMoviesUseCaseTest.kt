package com.example.moviesapp.domain.usecase

import com.example.moviesapp.domain.model.Movie
import com.example.moviesapp.domain.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SearchMoviesUseCaseTest {

    private lateinit var searchMoviesUseCase: SearchMoviesUseCase
    private lateinit var mockRepository: MovieRepository

    @Before
    fun setUp() {
        mockRepository = mockk()
        searchMoviesUseCase = SearchMoviesUseCase(mockRepository)
    }

    @Test
    fun `searchMovies with blank query returns empty list`() = runTest {
        // Given
        val blankQuery = ""

        // When
        val result = searchMoviesUseCase(blankQuery)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(emptyList<Movie>(), result.getOrNull())
    }

    @Test
    fun `searchMovies with whitespace query returns empty list`() = runTest {
        // Given
        val whitespaceQuery = "   "

        // When
        val result = searchMoviesUseCase(whitespaceQuery)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(emptyList<Movie>(), result.getOrNull())
    }

    @Test
    fun `searchMovies with valid query returns movies from repository`() = runTest {
        // Given
        val query = "Batman"
        val expectedMovies = listOf(
            Movie(
                imdbID = "tt0096895",
                title = "Batman",
                year = "1989",
                poster = "https://example.com/batman.jpg",
                type = "movie"
            )
        )
        coEvery {
            mockRepository.searchMovies(query)
        } returns Result.success(expectedMovies)

        // When
        val result = searchMoviesUseCase(query)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(expectedMovies, result.getOrNull())
    }

    @Test
    fun `searchMovies with random query returns movies from repository`() = runTest {
        //Given
        val query = listOf("matrix", "action", "comedy", "drama", "thriller").random()
        val expectedMovies = listOf(
            Movie(
                imdbID = "tt0096895",
                title = "Batman",
                year = "1989",
                poster = "https://example.com/batman.jpg",
                type = "movie"
            )
        )
        coEvery {
            mockRepository.searchMovies(query)
        } returns Result.success(expectedMovies)

        //When
        val result = searchMoviesUseCase(query)

        //Then
        assertTrue(result.isSuccess)
        assertEquals(expectedMovies, result.getOrNull())
    }

    @Test
    fun `searchMovies when repository fails returns failure`() = runTest {
        // Given
        val query = "Invalid"
        val expectedError = Exception("Network error")
        coEvery { mockRepository.searchMovies(query) } returns Result.failure(expectedError)

        // When
        val result = searchMoviesUseCase(query)

        // Then
        assertTrue(result.isFailure)
        assertEquals(expectedError, result.exceptionOrNull())
    }
} 