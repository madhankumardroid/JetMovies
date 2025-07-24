package com.example.moviesapp.data.repository

import com.example.moviesapp.data.local.MovieDao
import com.example.moviesapp.data.local.entity.MovieEntity
import com.example.moviesapp.data.local.entity.MovieDetailEntity
import com.example.moviesapp.data.remote.OmdbApiService
import com.example.moviesapp.domain.model.Movie
import com.example.moviesapp.domain.model.MovieDetail
import com.example.moviesapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: OmdbApiService,
    private val dao: MovieDao
) : MovieRepository {

    override suspend fun searchMovies(query: String): Result<List<Movie>> {
        return try {
            val response = api.searchMovies(query)
            if (response.Response == "True") {
                val movies = response.Search?.map { it.toMovie() } ?: emptyList()
                saveMovies(movies)
                Result.success(movies)
            } else {
                Result.failure(Exception("No movies found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMovieDetail(imdbId: String): Result<MovieDetail> {
        return try {
            val response = api.getMovieDetail(imdbId)
            if (response.Response == "True") {
                val movieDetail = response.toMovieDetail()
                saveMovieDetail(movieDetail)
                Result.success(movieDetail)
            } else {
                Result.failure(Exception("Movie not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun saveMovies(movies: List<Movie>) {
        val entities = movies.map { movie ->
            MovieEntity(
                imdbID = movie.imdbID,
                title = movie.title,
                year = movie.year,
                poster = movie.poster,
                type = movie.type
            )
        }
        dao.insertMovies(entities)
    }

    override suspend fun getSavedMovies(): Flow<List<Movie>> {
        return dao.getAllMovies().map { entities ->
            entities.map { entity ->
                Movie(
                    imdbID = entity.imdbID,
                    title = entity.title,
                    year = entity.year,
                    poster = entity.poster,
                    type = entity.type
                )
            }
        }
    }

    override suspend fun saveMovieDetail(movieDetail: MovieDetail) {
        val entity = MovieDetailEntity(
            imdbID = movieDetail.imdbID,
            title = movieDetail.title,
            year = movieDetail.year,
            rated = movieDetail.rated,
            released = movieDetail.released,
            runtime = movieDetail.runtime,
            genre = movieDetail.genre,
            director = movieDetail.director,
            writer = movieDetail.writer,
            actors = movieDetail.actors,
            plot = movieDetail.plot,
            poster = movieDetail.poster,
            imdbRating = movieDetail.imdbRating,
            imdbVotes = movieDetail.imdbVotes,
            type = movieDetail.type,
            dvd = movieDetail.dvd,
            boxOffice = movieDetail.boxOffice,
            production = movieDetail.production,
            website = movieDetail.website
        )
        dao.insertMovieDetail(entity)
    }

    override suspend fun getSavedMovieDetail(imdbId: String): MovieDetail? {
        return dao.getMovieDetail(imdbId)?.let { entity ->
            MovieDetail(
                imdbID = entity.imdbID,
                title = entity.title,
                year = entity.year,
                rated = entity.rated,
                released = entity.released,
                runtime = entity.runtime,
                genre = entity.genre,
                director = entity.director,
                writer = entity.writer,
                actors = entity.actors,
                plot = entity.plot,
                poster = entity.poster,
                ratings = emptyList(), // Ratings are not stored in local DB for simplicity
                imdbRating = entity.imdbRating,
                imdbVotes = entity.imdbVotes,
                type = entity.type,
                dvd = entity.dvd,
                boxOffice = entity.boxOffice,
                production = entity.production,
                website = entity.website
            )
        }
    }
} 