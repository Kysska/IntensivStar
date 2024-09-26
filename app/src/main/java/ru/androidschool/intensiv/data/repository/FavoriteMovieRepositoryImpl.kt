package ru.androidschool.intensiv.data.repository

import io.reactivex.Observable
import ru.androidschool.intensiv.data.local.dao.MovieDetailDao
import ru.androidschool.intensiv.data.local.mapper.MovieWithCastMapper
import ru.androidschool.intensiv.data.repository.base.BaseRepository
import ru.androidschool.intensiv.domain.FavoriteMovieRepository
import ru.androidschool.intensiv.domain.entity.MovieWithCast

class FavoriteMovieRepositoryImpl(
    private val movieDetailDao: MovieDetailDao,
    private val databaseMapper: MovieWithCastMapper
) :
    BaseRepository<List<MovieWithCast>>(), FavoriteMovieRepository {

    override fun getAllFavoriteMovieList(): Observable<List<MovieWithCast>> {
        return fetchData(
            call = { movieDetailDao.getAllFavoriteMoviesWithCast() },
            mapper = { databaseMapper.reverseMap(it) },
            tag = REPOSITORY_TAG
        )
    }

    companion object {
        private const val REPOSITORY_TAG = "FavoriteMovieRepositoryImpl"
    }
}
