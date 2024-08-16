package ru.androidschool.intensiv.data.network.mapper

import ru.androidschool.intensiv.data.network.dto.MovieResponse
import ru.androidschool.intensiv.domain.entity.MovieCard

object MovieCardMapper : ViewObjectMapper<MovieCard, MovieResponse> {
    override fun toViewObject(dto: MovieResponse): MovieCard {
        return MovieCard(
            id = dto.id ?: 0,
            title = dto.title ?: "",
            posterPath = dto.posterPath ?: "",
            voteAverage = dto.voteAverage ?: 0.0
        )
    }
}
