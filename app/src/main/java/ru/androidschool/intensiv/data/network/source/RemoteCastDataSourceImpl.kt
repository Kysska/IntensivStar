package ru.androidschool.intensiv.data.network.source

import io.reactivex.Single
import ru.androidschool.intensiv.data.network.MovieApiInterface
import ru.androidschool.intensiv.data.network.mapper.CastCardNetworkMapper
import ru.androidschool.intensiv.data.repository.cast.RemoteCastDataSource
import ru.androidschool.intensiv.domain.entity.CastCard

class RemoteCastDataSourceImpl(
    private val movieApiInterface: MovieApiInterface,
    private val networkMapper: CastCardNetworkMapper
) : RemoteCastDataSource, BaseRemoteDataSource<List<CastCard>>() {
    override fun fetchCasts(id: Int): Single<List<CastCard>> {
        return fetchData(
            call = { movieApiInterface.getCastsListById(id) },
            mapper = { response -> networkMapper.map(response.castsList ?: emptyList()) },
            emptyResult = emptyList(),
            tag = "Cast"
        )
    }
}
