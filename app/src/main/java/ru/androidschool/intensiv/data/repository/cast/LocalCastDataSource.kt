package ru.androidschool.intensiv.data.repository.cast

import io.reactivex.Completable
import ru.androidschool.intensiv.domain.entity.CastCard

interface LocalCastDataSource {
    fun saveCast(castCard: List<CastCard>): Completable
}
