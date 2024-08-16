package ru.androidschool.intensiv.data.network.mapper

import ru.androidschool.intensiv.data.network.dto.CastResponse
import ru.androidschool.intensiv.domain.entity.CastCard

object CastCardMapper : ViewObjectMapper<CastCard, CastResponse> {
    override fun toViewObject(dto: CastResponse): CastCard {
        return CastCard(
            id = dto.castId ?: 0,
            name = dto.name ?: "",
            posterPath = dto.profilePath ?: ""
        )
    }
}
