package ru.androidschool.intensiv.data.repository

import io.reactivex.Completable
import io.reactivex.Single
import ru.androidschool.intensiv.data.local.dao.MovieCardDao
import ru.androidschool.intensiv.data.local.mapper.MovieCardDatabaseMapper
import ru.androidschool.intensiv.data.network.MovieApiInterface
import ru.androidschool.intensiv.data.network.mapper.MovieCardNetworkMapper
import ru.androidschool.intensiv.data.repository.base.MovieCardBaseRepository
import ru.androidschool.intensiv.domain.MovieRepository
import ru.androidschool.intensiv.domain.entity.MovieCard

class TvShowRepositoryImpl(
    private val movieApiInterface: MovieApiInterface,
    private val movieCardDao: MovieCardDao,
    private val databaseMapper: MovieCardDatabaseMapper,
    private val networkMapper: MovieCardNetworkMapper
) :
    MovieCardBaseRepository(movieCardDao, databaseMapper, networkMapper), MovieRepository {

    override fun getMoviesFromNetwork(): Single<List<MovieCard>> {
        return fetchMoviesFromNetwork(
            apiCall = { movieApiInterface.getPopularTvShows() },
            tag = REPOSITORY_TAG
        )
    }

    override fun getMoviesFromLocalByCategory(): Single<List<MovieCard>> {
        return fetchMoviesFromLocal(
            category = CATEGORY_NAME,
            tag = REPOSITORY_TAG
        )
    }

    override fun saveMovie(movieCard: MovieCard): Completable {
        return saveMovieToLocal(
            movieCard = movieCard,
            tag = REPOSITORY_TAG,
            category = CATEGORY_NAME
        )
    }

    override fun getCategoryName(): String {
        return CATEGORY_NAME
    }

    companion object {
        const val REPOSITORY_TAG = "TvShowRepository"
        const val CATEGORY_NAME = "TvShow"
    }
}
