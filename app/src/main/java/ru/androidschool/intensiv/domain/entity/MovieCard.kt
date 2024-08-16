package ru.androidschool.intensiv.domain.entity

import ru.androidschool.intensiv.utils.extensions.toRating

data class MovieCard(
    val id: Int = 0,
    val title: String = "",
    val posterPath: String = "",
    val voteAverage: Double = 0.0
) {
    val rating: Float
        get() = voteAverage.toRating()
}
