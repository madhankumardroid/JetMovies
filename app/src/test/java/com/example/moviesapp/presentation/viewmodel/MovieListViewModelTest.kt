package com.example.moviesapp.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.example.moviesapp.domain.model.Movie
import com.example.moviesapp.domain.usecase.SearchMoviesUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MovieListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MovieListViewModel
    private lateinit var mockSearchMoviesUseCase: SearchMoviesUseCase
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        mockSearchMoviesUseCase = mockk()
        viewModel = MovieListViewModel(mockSearchMoviesUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is correct`() = runTest {
        viewModel.state.test {
            val initialState = awaitItem()
            assertEquals(emptyList<Movie>(), initialState.movies)
            assertFalse(initialState.isLoading)
            assertNull(initialState.error)
            assertEquals("", initialState.searchQuery)
        }
    }

    @Test
    fun `searchMovies with valid query updates state correctly`() = runTest {
        // Given
        val query = "Batman"
        val movies = listOf(
            Movie(
                imdbID = "tt0096895",
                title = "Batman",
                year = "1989",
                poster = "https://example.com/batman.jpg",
                type = "movie"
            )
        )
        coEvery { mockSearchMoviesUseCase(query) } returns Result.success(movies)

        // When
        viewModel.onEvent(MovieListEvent.SearchMovies(query))
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        viewModel.state.test {
            val state = awaitItem()
            assertEquals(movies, state.movies)
            assertFalse(state.isLoading)
            assertNull(state.error)
            assertEquals(query, state.searchQuery)
        }
    }

    @Test
    fun `searchMovies with error updates state correctly`() = runTest {
        // Given
        val query = "Invalid"
        val errorMessage = "Network error"
        coEvery { mockSearchMoviesUseCase(query) } returns Result.failure(Exception(errorMessage))

        // When
        viewModel.onEvent(MovieListEvent.SearchMovies(query))
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        viewModel.state.test {
            val state = awaitItem()
            assertEquals(emptyList<Movie>(), state.movies)
            assertFalse(state.isLoading)
            assertEquals(errorMessage, state.error)
            assertEquals(query, state.searchQuery)
        }
    }

    @Test
    fun `clearSearch resets state correctly`() = runTest {
        // Given
        val query = "Batman"
        val movies = listOf(
            Movie(
                imdbID = "tt0096895",
                title = "Batman",
                year = "1989",
                poster = "https://example.com/batman.jpg",
                type = "movie"
            )
        )
        coEvery { mockSearchMoviesUseCase(query) } returns Result.success(movies)

        // When
        viewModel.onEvent(MovieListEvent.SearchMovies(query))
        testDispatcher.scheduler.advanceUntilIdle()
        viewModel.onEvent(MovieListEvent.ClearSearch)

        // Then
        viewModel.state.test {
            val state = awaitItem()
            assertEquals(emptyList<Movie>(), state.movies)
            assertEquals("", state.searchQuery)
        }
    }

    @Test
    fun `search is debounced`() = runTest {
        // Given
        val query = "Batman"
        val movies = listOf(
            Movie(
                imdbID = "tt0096895",
                title = "Batman",
                year = "1989",
                poster = "https://example.com/batman.jpg",
                type = "movie"
            )
        )
        coEvery { mockSearchMoviesUseCase(query) } returns Result.success(movies)

        // When
        viewModel.onEvent(MovieListEvent.SearchMovies(query))
        // Don't advance time - search should not execute yet

        // Then
        viewModel.state.test {
            val state = awaitItem()
            assertTrue(state.isLoading) // Should be loading during debounce
            assertEquals(query, state.searchQuery)
        }
    }
} 