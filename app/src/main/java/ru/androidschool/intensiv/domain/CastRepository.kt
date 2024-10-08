package ru.androidschool.intensiv.domain

import io.reactivex.Completable
import io.reactivex.Single
import ru.androidschool.intensiv.domain.entity.CastCard

interface CastRepository {
    fun getCasts(id: Int): Single<List<CastCard>>

    fun saveCast(castCard: List<CastCard>): Completable
}
