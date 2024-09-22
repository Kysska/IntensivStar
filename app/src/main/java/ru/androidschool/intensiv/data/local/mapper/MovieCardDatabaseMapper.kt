package ru.androidschool.intensiv.data.local.mapper

import ru.androidschool.intensiv.data.local.dto.MovieCardDbEntity
import ru.androidschool.intensiv.domain.entity.MovieCard

object MovieCardDatabaseMapper : DatabaseMapper<MovieCard, MovieCardDbEntity> {
    override fun map(from: MovieCard): MovieCardDbEntity {
        return MovieCardDbEntity(
            id = from.id,
            title = from.title,
            posterPath = from.posterPath,
            rating = from.voteAverage,
            category = ""
        )
    }

    fun mapByCategory(from: MovieCard, category: String): MovieCardDbEntity {
        return MovieCardDbEntity(
            id = from.id,
            title = from.title,
            posterPath = from.posterPath,
            rating = from.voteAverage,
            category = category
        )
    }

    override fun reverseMap(to: MovieCardDbEntity): MovieCard {
        return MovieCard(
            id = to.id,
            title = to.title,
            posterPath = to.posterPath,
            voteAverage = to.rating
        )
    }
}
