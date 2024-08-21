package ru.androidschool.intensiv.data.repository

import io.reactivex.Single
import ru.androidschool.intensiv.data.network.MovieApiInterface
import ru.androidschool.intensiv.data.network.mapper.MovieCardMapper
import ru.androidschool.intensiv.domain.SearchRepository
import ru.androidschool.intensiv.domain.entity.MovieCard

class SearchRepositoryImpl(private val movieApiInterface: MovieApiInterface) :
    BaseRepository<List<MovieCard>>(), SearchRepository {

    override fun getSearchMovie(query: String): Single<List<MovieCard>> {
        return fetchData(
            apiCall = { movieApiInterface.searchMovieByTitle(query) },
            mapper = { response -> MovieCardMapper.toViewObject(response.results ?: emptyList()) },
            emptyResult = emptyList(),
            tag = REPOSITORY_TAG
        )
    }

    companion object {
        const val REPOSITORY_TAG = "SearchRepositoryImpl"
    }
}
