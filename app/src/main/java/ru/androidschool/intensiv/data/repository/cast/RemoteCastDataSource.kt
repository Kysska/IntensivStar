package ru.androidschool.intensiv.data.repository.cast

import io.reactivex.Single
import ru.androidschool.intensiv.domain.entity.CastCard

interface RemoteCastDataSource {
    fun fetchCasts(id: Int): Single<List<CastCard>>
}
