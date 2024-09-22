package ru.androidschool.intensiv.domain.entity

data class MovieWithCast(
    val movie: MovieDetail,
    val cast: List<CastCard>
)
