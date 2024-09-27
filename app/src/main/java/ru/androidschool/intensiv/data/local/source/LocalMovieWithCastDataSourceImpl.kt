package ru.androidschool.intensiv.data.local.source

import io.reactivex.Completable
import io.reactivex.Single
import ru.androidschool.intensiv.data.local.dao.MovieDetailDao
import ru.androidschool.intensiv.data.local.dto.MovieCastCrossRef
import ru.androidschool.intensiv.data.local.mapper.MovieWithCastMapper
import ru.androidschool.intensiv.data.repository.cast.LocalCastDataSource
import ru.androidschool.intensiv.data.repository.moviedetail.LocalMovieDetailDataSource
import ru.androidschool.intensiv.data.repository.moviewithcast.LocalMovieWithCastDataSource
import ru.androidschool.intensiv.domain.entity.MovieWithCast
import timber.log.Timber

class LocalMovieWithCastDataSourceImpl(
    private val movieDetailDataSource: LocalMovieDetailDataSource,
    private val castDataSource: LocalCastDataSource,
    private val movieDetailDao: MovieDetailDao,
    private val databaseMapper: MovieWithCastMapper
) : LocalMovieWithCastDataSource {
    override fun getMovieWithCast(id: Int): Single<MovieWithCast> {
        return movieDetailDao.getMovieWithCast(id)
            .map { response -> databaseMapper.reverseMap(response) }
            .doOnError { throwable ->
                Timber.tag("MovieWithCast").e(throwable)
            }
    }

    override fun saveMovieWithCast(movieWithCast: MovieWithCast): Completable {
        val movie = movieWithCast.movie
        val casts = movieWithCast.cast
        return movieDetailDataSource.saveMovieDetail(movie)
            .andThen(castDataSource.saveCast(casts))
            .andThen(
                Completable.fromAction {
                    casts.forEach { cast ->
                        val crossRef = MovieCastCrossRef(movie.id, cast.id)
                        movieDetailDao.insertMovieCastCrossRef(crossRef).subscribe()
                    }
                }
            )
            .doOnError { error ->
                Timber.e(error, "Error saving movie with cast")
            }
    }

    override fun deleteMovieWithCast(movieWithCast: MovieWithCast): Completable {
        val movie = movieWithCast.movie
        val casts = movieWithCast.cast

        return Completable.fromAction {
            casts.forEach { cast ->
                val crossRef = MovieCastCrossRef(movie.id, cast.id)
                movieDetailDao.deleteMovieCastCrossRef(crossRef).subscribe()
            }
        }
            .andThen(movieDetailDataSource.deleteMovieDetail(movie))
            .doOnError { error ->
                Timber.e(error, "Error deleting movie with cast")
            }
    }

    override fun isMovieExists(id: Int): Single<Boolean> {
        return movieDetailDao.isMovieExists(id)
    }
}
