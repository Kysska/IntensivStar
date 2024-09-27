package ru.androidschool.intensiv.data.local.source

import io.reactivex.Completable
import io.reactivex.Single
import ru.androidschool.intensiv.data.local.dao.MovieCardDao
import ru.androidschool.intensiv.data.local.mapper.MovieCardDatabaseMapper
import ru.androidschool.intensiv.data.repository.moviecard.LocalMovieCardDataSource
import ru.androidschool.intensiv.domain.entity.MovieCard
import ru.androidschool.intensiv.utils.MovieType
import timber.log.Timber

class LocalMovieCardDataSourceImpl(
    private val movieCardDao: MovieCardDao,
    private val databaseMapper: MovieCardDatabaseMapper
) : LocalMovieCardDataSource {
    override fun getListMovieCard(category: MovieType): Single<List<MovieCard>> {
        return movieCardDao.getAllMoviesCardByCategory(category.name)
            .map { databaseMapper.reverseMap(it) }
            .doOnError { throwable ->
                Timber.tag(category.name).e(throwable) }
    }

    override fun saveMovieCard(movieCard: MovieCard, category: MovieType): Completable {
        return movieCardDao.insertMovie(databaseMapper.mapByCategory(movieCard, category.name))
            .doOnComplete {
                Timber.tag(category.name).d("Insert completed successfully")
            }
            .doOnError { throwable ->
                Timber.tag(category.name).e(throwable, "Error Insert")
            }
    }
}
