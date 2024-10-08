package ru.androidschool.intensiv.data.local.source

import io.reactivex.Completable
import ru.androidschool.intensiv.data.local.dao.MovieDetailDao
import ru.androidschool.intensiv.data.local.mapper.CastDatabaseMapper
import ru.androidschool.intensiv.data.repository.cast.LocalCastDataSource
import ru.androidschool.intensiv.domain.entity.CastCard

class LocalCastDataSourceImpl(
    private val movieDetailDao: MovieDetailDao,
    private val databaseMapper: CastDatabaseMapper
) : LocalCastDataSource, BaseLocalDataSource<List<CastCard>>() {

    override fun saveCast(castCard: List<CastCard>): Completable {
        val castDbEntity = databaseMapper.map(castCard)
        val insertOperations = castDbEntity.map { movieDetailDao.insertCast(it) }
        return performOperation(
            dbOperation = { Completable.merge(insertOperations) },
            tag = "Cast",
            nameOperation = "Insert"
        )
    }
}
