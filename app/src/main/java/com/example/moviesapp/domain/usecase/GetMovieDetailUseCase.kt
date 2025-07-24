package com.example.moviesapp.domain.usecase

import com.example.moviesapp.domain.model.MovieDetail
import com.example.moviesapp.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(imdbId: String): Result<MovieDetail> {
        if (imdbId.isBlank()) {
            return Result.failure(IllegalArgumentException("IMDB ID cannot be blank"))
        }
        return repository.getMovieDetail(imdbId)
    }
} 