package ru.androidschool.intensiv.data.repository

import io.reactivex.Single
import ru.androidschool.intensiv.data.network.MovieApiInterface
import ru.androidschool.intensiv.data.network.mapper.CastCardMapper
import ru.androidschool.intensiv.domain.CastRepository
import ru.androidschool.intensiv.domain.entity.CastCard

class CastRepositoryImpl(private val movieApiInterface: MovieApiInterface) : BaseRepository<List<CastCard>>(), CastRepository {

    override fun getCasts(id: Int): Single<List<CastCard>> {
        return fetchData(
            apiCall = { movieApiInterface.getCastsListById(id) },
            mapper = { response -> CastCardMapper.toViewObject(response.castsList ?: emptyList()) },
            emptyResult = emptyList(),
            tag = REPOSITORY_TAG
        )
    }

    companion object {
        const val REPOSITORY_TAG = "CastRepository"
    }
}
