package ru.androidschool.intensiv.data.network.source

import io.reactivex.Single
import ru.androidschool.intensiv.data.network.MovieApiInterface
import ru.androidschool.intensiv.data.network.mapper.CastCardNetworkMapper
import ru.androidschool.intensiv.data.repository.cast.RemoteCastDataSource
import ru.androidschool.intensiv.domain.entity.CastCard
import timber.log.Timber

class RemoteCastDataSourceImpl(
    private val movieApiInterface: MovieApiInterface,
    private val networkMapper: CastCardNetworkMapper
) : RemoteCastDataSource {
    override fun fetchCasts(id: Int): Single<List<CastCard>> {
        return movieApiInterface.getCastsListById(id)
            .map { response -> networkMapper.map(response.castsList ?: emptyList()) }
            .doOnError { throwable ->
                Timber.tag("Cast").e(throwable)
            }
    }
}
