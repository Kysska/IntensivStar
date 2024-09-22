package ru.androidschool.intensiv.data.repository

import io.reactivex.Completable
import io.reactivex.Single
import ru.androidschool.intensiv.data.local.dao.MovieCardDao
import ru.androidschool.intensiv.data.network.MovieApiInterface
import ru.androidschool.intensiv.domain.MovieRepository
import ru.androidschool.intensiv.domain.entity.MovieCard

class TvShowRepositoryImpl(private val movieApiInterface: MovieApiInterface, private val movieCardDao: MovieCardDao) :
    MovieCardBaseRepository(movieCardDao), MovieRepository {

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

    companion object {
        const val REPOSITORY_TAG = "TvShowRepository"
        const val CATEGORY_NAME = "TvShow"
    }
}
