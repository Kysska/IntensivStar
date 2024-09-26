package ru.androidschool.intensiv.data.network.mapper

import ru.androidschool.intensiv.data.network.dto.CastResponse
import ru.androidschool.intensiv.domain.entity.CastCard

object CastCardNetworkMapper : NetworkMapper<CastCard, CastResponse> {
    override fun map(from: CastResponse): CastCard {
        return CastCard(
            id = from.castId ?: 0,
            name = from.name ?: "",
            posterPath = from.profilePath ?: ""
        )
    }
}
