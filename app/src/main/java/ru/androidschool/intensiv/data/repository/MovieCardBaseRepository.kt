package ru.androidschool.intensiv.data.repository

import io.reactivex.Completable
import io.reactivex.Single
import ru.androidschool.intensiv.data.local.dao.MovieCardDao
import ru.androidschool.intensiv.data.local.mapper.MovieCardDatabaseMapper
import ru.androidschool.intensiv.data.network.dto.MovieListResponse
import ru.androidschool.intensiv.data.network.mapper.MovieCardNetworkMapper
import ru.androidschool.intensiv.domain.entity.MovieCard

abstract class MovieCardBaseRepository(
    private val movieCardDao: MovieCardDao
) : BaseRepository<List<MovieCard>>() {

    protected fun fetchMoviesFromNetwork(
        apiCall: () -> Single<MovieListResponse>,
        tag: String
    ): Single<List<MovieCard>> {
        return fetchData(
            call = apiCall,
            mapper = { response -> MovieCardNetworkMapper.map(response.results ?: emptyList()) },
            emptyResult = emptyList(),
            tag = tag
        )
    }

    protected fun fetchMoviesFromLocal(
        category: String,
        tag: String
    ): Single<List<MovieCard>> {
        return fetchData(
            call = { movieCardDao.getAllMoviesCardByCategory(category) },
            mapper = { MovieCardDatabaseMapper.reverseMap(it) },
            emptyResult = emptyList(),
            tag = tag
        )
    }

    protected fun saveMovieToLocal(
        movieCard: MovieCard,
        tag: String,
        category: String
    ): Completable {
        return performDatabaseOperation(
            daoOperation = { movieCardDao.insertMovie(MovieCardDatabaseMapper.mapByCategory(movieCard, category)) },
            operationType = "Insert",
            tag = tag
        )
    }
}
