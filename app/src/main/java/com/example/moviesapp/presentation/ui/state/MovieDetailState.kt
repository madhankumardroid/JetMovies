package com.example.moviesapp.presentation.ui.state

import com.example.moviesapp.domain.model.MovieDetail

data class MovieDetailState(
    val movieDetail: MovieDetail? = null,
    val isLoading: Boolean = false,
    val error: String? = null
) 