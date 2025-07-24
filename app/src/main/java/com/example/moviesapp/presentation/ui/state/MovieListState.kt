package com.example.moviesapp.presentation.ui.state

import com.example.moviesapp.domain.model.Movie

data class MovieListState(
    val movies: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = ""
) 