package com.example.moviesapp.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.domain.usecase.GetMovieDetailUseCase
import com.example.moviesapp.presentation.ui.state.MovieDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(MovieDetailState())
    val state: StateFlow<MovieDetailState> = _state.asStateFlow()

    init {
        savedStateHandle.get<String>("imdbId")?.let { imdbId ->
            getMovieDetail(imdbId)
        }
    }

    private fun getMovieDetail(imdbId: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            
            getMovieDetailUseCase(imdbId).fold(
                onSuccess = { movieDetail ->
                    _state.update {
                        it.copy(
                            movieDetail = movieDetail,
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