package com.example.moviesapp.presentation.ui.screen

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.moviesapp.domain.model.Movie
import com.example.moviesapp.presentation.viewmodel.MovieListEvent
import com.example.moviesapp.presentation.viewmodel.MovieListViewModel
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun movieListScreen_displaysSearchBar() {
        // Given
        val mockViewModel = mockk<MovieListViewModel>(relaxed = true)
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        // When
        composeTestRule.setContent {
            MovieListScreen(
                navController = navController,
                viewModel = mockViewModel
            )
        }

        // Then
        composeTestRule.onNodeWithText("Search movies…").assertExists()
    }

    @Test
    fun movieListScreen_displaysLoadingState() {
        // Given
        val mockViewModel = mockk<MovieListViewModel>()
        val state = MutableStateFlow(
            com.example.moviesapp.presentation.ui.state.MovieListState(
                isLoading = true
            )
        )
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        // When
        composeTestRule.setContent {
            MovieListScreen(
                navController = navController,
                viewModel = mockViewModel
            )
        }

        // Then
        composeTestRule.onNodeWithTag("CircularProgressIndicator").assertExists()
    }

    @Test
    fun movieListScreen_displaysMovies() {
        // Given
        val mockViewModel = mockk<MovieListViewModel>()
        val movies = listOf(
            Movie(
                imdbID = "tt0096895",
                title = "Batman",
                year = "1989",
                poster = "https://example.com/batman.jpg",
                type = "movie"
            )
        )
        val state = MutableStateFlow(
            com.example.moviesapp.presentation.ui.state.MovieListState(
                movies = movies
            )
        )
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        // When
        composeTestRule.setContent {
            MovieListScreen(
                navController = navController,
                viewModel = mockViewModel
            )
        }

        // Then
        composeTestRule.onNodeWithText("Batman").assertExists()
        composeTestRule.onNodeWithText("1989").assertExists()
    }

    @Test
    fun movieListScreen_displaysErrorState() {
        // Given
        val mockViewModel = mockk<MovieListViewModel>()
        val state = MutableStateFlow(
            com.example.moviesapp.presentation.ui.state.MovieListState(
                error = "Network error"
            )
        )
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        // When
        composeTestRule.setContent {
            MovieListScreen(
                navController = navController,
                viewModel = mockViewModel
            )
        }

        // Then
        composeTestRule.onNodeWithText("Network error").assertExists()
        composeTestRule.onNodeWithText("Retry").assertExists()
    }

    @Test
    fun movieListScreen_displaysEmptyState() {
        // Given
        val mockViewModel = mockk<MovieListViewModel>()
        val state = MutableStateFlow(
            com.example.moviesapp.presentation.ui.state.MovieListState(
                searchQuery = "Invalid",
                movies = emptyList()
            )
        )
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        // When
        composeTestRule.setContent {
            MovieListScreen(
                navController = navController,
                viewModel = mockViewModel
            )
        }

        // Then
        composeTestRule.onNodeWithText("No movies found").assertExists()
    }
} 