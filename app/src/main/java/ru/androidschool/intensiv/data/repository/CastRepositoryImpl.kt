package ru.androidschool.intensiv.data.repository

import io.reactivex.Completable
import io.reactivex.Single
import ru.androidschool.intensiv.data.local.dao.MovieDetailDao
import ru.androidschool.intensiv.data.local.mapper.CastDatabaseMapper
import ru.androidschool.intensiv.data.network.MovieApiInterface
import ru.androidschool.intensiv.data.network.mapper.CastCardNetworkMapper
import ru.androidschool.intensiv.data.repository.base.BaseRepository
import ru.androidschool.intensiv.domain.CastRepository
import ru.androidschool.intensiv.domain.entity.CastCard

class CastRepositoryImpl(
    private val movieApiInterface: MovieApiInterface,
    private val movieDetailDao: MovieDetailDao,
    private val databaseMapper: CastDatabaseMapper,
    private val networkMapper: CastCardNetworkMapper
) : BaseRepository<List<CastCard>>(), CastRepository {

    override fun getCasts(id: Int): Single<List<CastCard>> {
        return fetchData(
            call = { movieApiInterface.getCastsListById(id) },
            mapper = { response -> networkMapper.map(response.castsList ?: emptyList()) },
            emptyResult = emptyList(),
            tag = REPOSITORY_TAG
        )
    }

    override fun saveCast(castCard: List<CastCard>): Completable {
        val castDbEntity = databaseMapper.map(castCard)
        val insertOperations = castDbEntity.map { movieDetailDao.insertCast(it) }

        return performDatabaseOperation(
            daoOperation = { Completable.merge(insertOperations) },
            operationType = "Insert",
            tag = MovieDetailRepositoryImpl.REPOSITORY_TAG
        )
    }

    companion object {
        const val REPOSITORY_TAG = "CastRepository"
    }
}
