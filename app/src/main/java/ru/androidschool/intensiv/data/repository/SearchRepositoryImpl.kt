package ru.androidschool.intensiv.data.repository

import io.reactivex.Single
import ru.androidschool.intensiv.data.network.MovieApiInterface
import ru.androidschool.intensiv.data.network.mapper.MovieCardNetworkMapper
import ru.androidschool.intensiv.domain.SearchRepository
import ru.androidschool.intensiv.domain.entity.MovieCard
import timber.log.Timber

class SearchRepositoryImpl(
    private val movieApiInterface: MovieApiInterface,
    private val networkMapper: MovieCardNetworkMapper
) : SearchRepository {

    override fun getSearchMovie(query: String): Single<List<MovieCard>> {
        return movieApiInterface.searchMovieByTitle(query)
            .map { response -> networkMapper.map(response.results ?: emptyList()) }
            .doOnError { throwable ->
                Timber.tag("MovieWithCast").e(throwable)
            }
    }
}
