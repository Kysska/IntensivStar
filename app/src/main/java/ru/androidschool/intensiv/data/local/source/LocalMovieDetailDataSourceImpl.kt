package ru.androidschool.intensiv.data.local.source

import io.reactivex.Completable
import ru.androidschool.intensiv.data.local.dao.MovieDetailDao
import ru.androidschool.intensiv.data.local.mapper.MovieDetailDatabaseMapper
import ru.androidschool.intensiv.data.repository.moviedetail.LocalMovieDetailDataSource
import ru.androidschool.intensiv.domain.entity.MovieDetail

class LocalMovieDetailDataSourceImpl(
    private val movieDetailDao: MovieDetailDao,
    private val databaseMapper: MovieDetailDatabaseMapper
) : LocalMovieDetailDataSource, BaseLocalDataSource<MovieDetail>() {

    override fun saveMovieDetail(movieDetail: MovieDetail): Completable {
        val movieDbEntity = databaseMapper.map(movieDetail)
        return performOperation(
            dbOperation = { movieDetailDao.insertMovie(movieDbEntity) },
            tag = "MovieDetail",
            nameOperation = "Insert"
        )
    }

    override fun deleteMovieDetail(movieDetail: MovieDetail): Completable {
        val movieDbEntity = databaseMapper.map(movieDetail)
        return performOperation(
            dbOperation = { movieDetailDao.deleteMovie(movieDbEntity) },
            tag = "MovieDetail",
            nameOperation = "Insert"
        )
    }
}
