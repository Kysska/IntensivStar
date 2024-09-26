package ru.androidschool.intensiv.data.network.mapper

import ru.androidschool.intensiv.data.network.dto.MovieResponse
import ru.androidschool.intensiv.domain.entity.MovieCard

object MovieCardNetworkMapper : NetworkMapper<MovieCard, MovieResponse> {
    override fun map(from: MovieResponse): MovieCard {
        return MovieCard(
            id = from.id ?: 0,
            title = from.title ?: "",
            posterPath = from.posterPath ?: "",
            voteAverage = from.voteAverage ?: 0.0
        )
    }
}
