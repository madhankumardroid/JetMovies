package com.example.moviesapp.domain.usecase

import com.example.moviesapp.domain.model.MovieDetail
import com.example.moviesapp.domain.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetMovieDetailUseCaseTest {

    private lateinit var getMovieDetailUseCase: GetMovieDetailUseCase
    private lateinit var mockRepository: MovieRepository

    @Before
    fun setUp() {
        mockRepository = mockk()
        getMovieDetailUseCase = GetMovieDetailUseCase(mockRepository)
    }

    @Test
    fun `getMovieDetail with blank imdbId returns failure`() = runTest {
        // Given
        val blankImdbId = ""

        // When
        val result = getMovieDetailUseCase(blankImdbId)

        // Then
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is IllegalArgumentException)
    }

    @Test
    fun `getMovieDetail with valid imdbId returns movie detail from repository`() = runTest {
        // Given
        val imdbId = "tt0096895"
        val expectedMovieDetail = MovieDetail(
            imdbID = "tt0096895",
            title = "Batman",
            year = "1989",
            rated = "PG-13",
            released = "23 Jun 1989",
            runtime = "126 min",
            genre = "Action, Adventure",
            director = "Tim Burton",
            writer = "Bob Kane, Sam Hamm",
            actors = "Michael Keaton, Jack Nicholson, Kim Basinger",
            plot = "The Dark Knight of Gotham City begins his war on crime...",
            poster = "https://example.com/batman.jpg",
            ratings = emptyList(),
            imdbRating = "7.5",
            imdbVotes = "350,000",
            type = "movie",
            dvd = "25 Sep 2001",
            boxOffice = "$251,188,924",
            production = "Warner Bros.",
            website = "N/A"
        )
        coEvery { mockRepository.getMovieDetail(imdbId) } returns Result.success(expectedMovieDetail)

        // When
        val result = getMovieDetailUseCase(imdbId)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(expectedMovieDetail, result.getOrNull())
    }

    @Test
    fun `getMovieDetail when repository fails returns failure`() = runTest {
        // Given
        val imdbId = "invalid_id"
        val expectedError = Exception("Movie not found")
        coEvery { mockRepository.getMovieDetail(imdbId) } returns Result.failure(expectedError)

        // When
        val result = getMovieDetailUseCase(imdbId)

        // Then
        assertTrue(result.isFailure)
        assertEquals(expectedError, result.exceptionOrNull())
    }
} 