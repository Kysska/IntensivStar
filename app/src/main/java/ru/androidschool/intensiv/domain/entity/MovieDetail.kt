package ru.androidschool.intensiv.domain.entity

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
        get() = voteAverage.div(2).toFloat()
}
