package ru.androidschool.intensiv.data.repository

import io.reactivex.Observable
import ru.androidschool.intensiv.data.local.dao.MovieDetailDao
import ru.androidschool.intensiv.data.local.mapper.MovieWithCastMapper
import ru.androidschool.intensiv.domain.FavoriteMovieRepository
import ru.androidschool.intensiv.domain.entity.MovieWithCast
import timber.log.Timber

class FavoriteMovieRepositoryImpl(
    private val movieDetailDao: MovieDetailDao,
    private val databaseMapper: MovieWithCastMapper
) :
    FavoriteMovieRepository {

    override fun getAllFavoriteMovieList(): Observable<List<MovieWithCast>> {
        return movieDetailDao.getAllFavoriteMoviesWithCast()
            .map { databaseMapper.reverseMap(it) }
            .doOnError { throwable ->
                Timber.tag("MovieWithCast").e(throwable)
            }
    }
}
