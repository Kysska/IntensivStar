package ru.androidschool.intensiv.data.repository

import io.reactivex.Observable
import ru.androidschool.intensiv.data.local.dao.MovieDetailDao
import ru.androidschool.intensiv.data.local.mapper.MovieWithCastMapper
import ru.androidschool.intensiv.data.local.source.BaseLocalDataSource
import ru.androidschool.intensiv.domain.FavoriteMovieRepository
import ru.androidschool.intensiv.domain.entity.MovieWithCast

class FavoriteMovieRepositoryImpl(
    private val movieDetailDao: MovieDetailDao,
    private val databaseMapper: MovieWithCastMapper
) :
    FavoriteMovieRepository, BaseLocalDataSource<List<MovieWithCast>>() {

    override fun getAllFavoriteMovieList(): Observable<List<MovieWithCast>> {
        return fetchData(
            call = { movieDetailDao.getAllFavoriteMoviesWithCast() },
            mapper = { databaseMapper.reverseMap(it) },
            tag = "FavoriteMovie"
        )
    }
}
