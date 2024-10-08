package ru.androidschool.intensiv.data.repository

import io.reactivex.Single
import ru.androidschool.intensiv.data.network.MovieApiInterface
import ru.androidschool.intensiv.data.network.mapper.MovieCardNetworkMapper
import ru.androidschool.intensiv.data.network.source.BaseRemoteDataSource
import ru.androidschool.intensiv.domain.SearchRepository
import ru.androidschool.intensiv.domain.entity.MovieCard

class SearchRepositoryImpl(
    private val movieApiInterface: MovieApiInterface,
    private val networkMapper: MovieCardNetworkMapper
) : SearchRepository, BaseRemoteDataSource<List<MovieCard>>() {

    override fun getSearchMovie(query: String): Single<List<MovieCard>> {
        return fetchData(
            call = { movieApiInterface.searchMovieByTitle(query) },
            mapper = { response -> networkMapper.map(response.results ?: emptyList()) },
            emptyResult = emptyList(),
            tag = "Search"
        )
    }
}
