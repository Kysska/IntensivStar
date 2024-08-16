package ru.androidschool.intensiv.data.repository

import ru.androidschool.intensiv.data.network.MovieApiInterface
import ru.androidschool.intensiv.data.network.mapper.CastCardMapper
import ru.androidschool.intensiv.data.network.util.CustomResult
import ru.androidschool.intensiv.domain.CastRepository
import ru.androidschool.intensiv.domain.entity.CastCard

class CastRepositoryImpl(private val movieApiInterface: MovieApiInterface) : BaseRepository(), CastRepository {

    override fun getCasts(id: Int, callback: (CustomResult<List<CastCard>>) -> Unit) {
        handleResponse(
            call = movieApiInterface.getCastsListById(id),
            mapper = { response -> response.castsList?.map { CastCardMapper.toViewObject(it) } ?: emptyList() },
            callback = callback
        )
    }
}
