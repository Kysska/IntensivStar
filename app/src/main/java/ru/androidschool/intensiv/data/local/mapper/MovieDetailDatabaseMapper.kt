package ru.androidschool.intensiv.data.local.mapper

import ru.androidschool.intensiv.data.local.dto.MovieDbEntity
import ru.androidschool.intensiv.domain.entity.MovieDetail

object MovieDetailDatabaseMapper : DatabaseMapper<MovieDetail, MovieDbEntity> {

    override fun map(from: MovieDetail): MovieDbEntity {
        return MovieDbEntity(
            id = from.id,
            title = from.title,
            posterPath = from.posterPath,
            rating = from.voteAverage,
            overview = from.overview,
            productionCompanies = from.productionCompanies.joinToString(", "),
            genre = from.genres.joinToString(", "),
            releaseDate = from.releaseDate
        )
    }

    override fun reverseMap(to: MovieDbEntity): MovieDetail {
        return MovieDetail(
            id = to.id,
            title = to.title,
            posterPath = to.posterPath,
            voteAverage = to.rating,
            overview = to.overview,
            productionCompanies = to.productionCompanies.split(", "),
            genres = to.genre.split(", "),
            releaseDate = to.releaseDate
        )
    }
}
