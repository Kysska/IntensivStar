package ru.androidschool.intensiv.data.local.source

import io.reactivex.Completable
import ru.androidschool.intensiv.data.local.dao.MovieDetailDao
import ru.androidschool.intensiv.data.local.mapper.MovieDetailDatabaseMapper
import ru.androidschool.intensiv.data.repository.moviedetail.LocalMovieDetailDataSource
import ru.androidschool.intensiv.domain.entity.MovieDetail
import timber.log.Timber

class LocalMovieDetailDataSourceImpl(
    private val movieDetailDao: MovieDetailDao,
    private val databaseMapper: MovieDetailDatabaseMapper
) : LocalMovieDetailDataSource {

    override fun saveMovieDetail(movieDetail: MovieDetail): Completable {
        val movieDbEntity = databaseMapper.map(movieDetail)
        return movieDetailDao.insertMovie(movieDbEntity)
            .doOnComplete {
                Timber.tag("MovieDetail").d("Insert completed successfully")
            }
            .doOnError { throwable ->
                Timber.tag("MovieDetail").e(throwable, "Error Insert")
            }
    }

    override fun deleteMovieDetail(movieDetail: MovieDetail): Completable {
        val movieDbEntity = databaseMapper.map(movieDetail)
        return movieDetailDao.deleteMovie(movieDbEntity)
            .doOnComplete {
                Timber.tag("MovieDetail").d("Delete completed successfully")
            }
            .doOnError { throwable ->
                Timber.tag("MovieDetail").e(throwable, "Error Delete")
            }
    }
}
