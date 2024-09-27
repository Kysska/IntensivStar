package ru.androidschool.intensiv.data.local.source

import io.reactivex.Completable
import ru.androidschool.intensiv.data.local.dao.MovieDetailDao
import ru.androidschool.intensiv.data.local.mapper.CastDatabaseMapper
import ru.androidschool.intensiv.data.repository.cast.LocalCastDataSource
import ru.androidschool.intensiv.domain.entity.CastCard
import timber.log.Timber

class LocalCastDataSourceImpl(
    private val movieDetailDao: MovieDetailDao,
    private val databaseMapper: CastDatabaseMapper
) : LocalCastDataSource {

    override fun saveCast(castCard: List<CastCard>): Completable {
        val castDbEntity = databaseMapper.map(castCard)
        val insertOperations = castDbEntity.map { movieDetailDao.insertCast(it) }
        return Completable.merge(insertOperations)
            .doOnComplete {
                Timber.tag("Cast").d("Insert completed successfully")
            }
            .doOnError { throwable ->
                Timber.tag("Cast").e(throwable, "Error Insert")
            }
    }
}
