package ru.androidschool.intensiv.domain.entity

import ru.androidschool.intensiv.utils.extensions.toRating

data class MovieDetail(
    val id: Int = 0,
    val title: String = "",
    val overview: String = "",
    val releaseDate: String = "",
    val voteAverage: Double = 0.0,
    val posterPath: String = "",
    val genres: List<String> = emptyList(),
    val productionCompanies: List<String> = emptyList()
) {
    val rating: Float
        get() = voteAverage.toRating()
}
