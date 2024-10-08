package ru.androidschool.intensiv.data.repository.cast

import io.reactivex.Completable
import io.reactivex.Single
import ru.androidschool.intensiv.domain.CastRepository
import ru.androidschool.intensiv.domain.entity.CastCard
import java.util.Deque
import java.util.LinkedList

class CastRepositoryImpl(
    private val remoteCastDataSource: RemoteCastDataSource,
    private val localCastDataSource: LocalCastDataSource
) : CastRepository {

    override fun getCasts(id: Int): Single<List<CastCard>> {
        return remoteCastDataSource.fetchCasts(id)
    }

    override fun saveCast(castCard: List<CastCard>): Completable {
        return localCastDataSource.saveCast(castCard)
    }
}
