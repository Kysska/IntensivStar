package ru.androidschool.intensiv.data.local.mapper

import ru.androidschool.intensiv.data.local.dto.CastDbEntity
import ru.androidschool.intensiv.domain.entity.CastCard

object CastDatabaseMapper : DatabaseMapper<CastCard, CastDbEntity> {

    override fun map(from: CastCard): CastDbEntity {
        return CastDbEntity(
            id = from.id,
            name = from.name,
            posterPath = from.posterPath
        )
    }

    override fun reverseMap(to: CastDbEntity): CastCard {
        return CastCard(
            id = to.id,
            name = to.name,
            posterPath = to.posterPath
        )
    }
}
