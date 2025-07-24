package com.example.moviesapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.domain.usecase.SearchMoviesUseCase
import com.example.moviesapp.presentation.ui.state.MovieListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val searchMoviesUseCase: SearchMoviesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(MovieListState())
    val state: StateFlow<MovieListState> = _state.asStateFlow()

    private var searchJob: Job? = null
    private var fetchRandomMoviesJob: Job? = null

    private val keywords = listOf("matrix", "action", "comedy", "drama", "thriller")

    init {
        fetchRandomMovies()
    }

    private fun fetchRandomMovies() {
        fetchRandomMoviesJob?.cancel()
        fetchRandomMoviesJob = viewModelScope.launch {
            searchMoviesUseCase(keywords.random()).fold(
                onSuccess = { movies ->
                    _state.update {
                        it.copy(
                            movies = movies,
                            isLoading = false,
                            error = null
                        )
                    }
                },
                onFailure = { exception ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = exception.message ?: "An error occurred"
                        )
                    }
                }
            )
        }
    }

    fun onEvent(event: MovieListEvent) {
        when (event) {
            is MovieListEvent.SearchMovies -> {
                searchMovies(event.query)
            }
            is MovieListEvent.ClearSearch -> {
                _state.update { it.copy(searchQuery = "", movies = emptyList()) }
            }
        }
    }

    private fun searchMovies(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null, searchQuery = query) }
            
            delay(500) // Debounce search
            
            searchMoviesUseCase(query).fold(
                onSuccess = { movies ->
                    _state.update {
                        it.copy(
                            movies = movies,
                            isLoading = false,
                            error = null
                        )
                    }
                },
                onFailure = { exception ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = exception.message ?: "An error occurred"
                        )
                    }
                }
            )
        }
    }
}

sealed class MovieListEvent {
    data class SearchMovies(val query: String) : MovieListEvent()
    object ClearSearch : MovieListEvent()
} 