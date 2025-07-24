package com.example.moviesapp.domain.usecase

import android.util.Log
import com.example.moviesapp.domain.model.Movie
import com.example.moviesapp.domain.repository.MovieRepository
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(query: String): Result<List<Movie>> {
//        Log.e("CHECK_TEST_CASE", "invoke: SearchMoviesUseCase called with query = $query", )
        if (query.isBlank()) {
            return Result.success(emptyList())
        }
        return repository.searchMovies(query)
    }
} 