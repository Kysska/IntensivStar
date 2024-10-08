package ru.androidschool.intensiv.data.local.source

import io.reactivex.Completable
import io.reactivex.Single
import ru.androidschool.intensiv.data.local.dao.MovieDetailDao
import ru.androidschool.intensiv.data.local.dto.MovieCastCrossRef
import ru.androidschool.intensiv.data.local.mapper.MovieWithCastMapper
import ru.androidschool.intensiv.data.repository.cast.LocalCastDataSource
import ru.androidschool.intensiv.data.repository.moviedetail.LocalMovieDetailDataSource
import ru.androidschool.intensiv.data.repository.moviewithcast.LocalMovieWithCastDataSource
import ru.androidschool.intensiv.domain.entity.MovieDetail
import ru.androidschool.intensiv.domain.entity.MovieWithCast

class LocalMovieWithCastDataSourceImpl(
    private val movieDetailDataSource: LocalMovieDetailDataSource,
    private val castDataSource: LocalCastDataSource,
    private val movieDetailDao: MovieDetailDao,
    private val databaseMapper: MovieWithCastMapper
) : LocalMovieWithCastDataSource, BaseLocalDataSource<MovieWithCast>() {

    override fun getMovieWithCast(id: Int): Single<MovieWithCast> {
        return fetchData(
            call = { movieDetailDao.getMovieWithCast(id) },
            mapper = { response -> databaseMapper.reverseMap(response) },
            emptyResult = MovieWithCast(MovieDetail(), emptyList()),
            tag = "MovieWithCast"
        )
    }

    override fun saveMovieWithCast(movieWithCast: MovieWithCast): Completable {
        val movie = movieWithCast.movie
        val casts = movieWithCast.cast
        return performOperation(
            dbOperation = {
                movieDetailDataSource.saveMovieDetail(movie)
                    .andThen(castDataSource.saveCast(casts))
                    .andThen(
                        Completable.fromAction {
                            casts.forEach { cast ->
                                val crossRef = MovieCastCrossRef(movie.id, cast.id)
                                movieDetailDao.insertMovieCastCrossRef(crossRef).subscribe()
                            }
                        }
                    )
            },
            tag = "MovieWithCast",
            nameOperation = "Insert"
        )
    }

    override fun deleteMovieWithCast(movieWithCast: MovieWithCast): Completable {
        val movie = movieWithCast.movie
        val casts = movieWithCast.cast
        return performOperation(
            dbOperation = {
                Completable.fromAction {
                    casts.forEach { cast ->
                        val crossRef = MovieCastCrossRef(movie.id, cast.id)
                        movieDetailDao.deleteMovieCastCrossRef(crossRef).subscribe()
                    }
                }
                    .andThen(movieDetailDataSource.deleteMovieDetail(movie))
            },
            tag = "MovieWithCast",
            nameOperation = "Delete"
        )
    }

    override fun isMovieExists(id: Int): Single<Boolean> {
        return movieDetailDao.isMovieExists(id)
    }
}
