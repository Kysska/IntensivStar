package ru.androidschool.intensiv.data.local.source

import io.reactivex.Completable
import io.reactivex.Single
import ru.androidschool.intensiv.data.local.dao.MovieCardDao
import ru.androidschool.intensiv.data.local.mapper.MovieCardDatabaseMapper
import ru.androidschool.intensiv.data.repository.moviecard.LocalMovieCardDataSource
import ru.androidschool.intensiv.domain.entity.MovieCard
import ru.androidschool.intensiv.utils.MovieType

class LocalMovieCardDataSourceImpl(
    private val movieCardDao: MovieCardDao,
    private val databaseMapper: MovieCardDatabaseMapper
) : LocalMovieCardDataSource, BaseLocalDataSource<List<MovieCard>>() {

    override fun getListMovieCard(category: MovieType): Single<List<MovieCard>> {
        return fetchData(
            call = { movieCardDao.getAllMoviesCardByCategory(category.name) },
            mapper = { response -> databaseMapper.reverseMap(response) },
            emptyResult = emptyList(),
            tag = category.name
        )
    }

    override fun saveMovieCard(movieCard: MovieCard, category: MovieType): Completable {
        return performOperation(
            dbOperation = { movieCardDao.insertMovie(databaseMapper.mapByCategory(movieCard, category.name)) },
            tag = category.name,
            nameOperation = "Insert"
        )
    }
}
