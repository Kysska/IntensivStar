package ru.androidschool.intensiv.data.network.mapper

import ru.androidschool.intensiv.utils.extensions.parseYearFromDate
import ru.androidschool.intensiv.data.network.dto.MovieDetailResponse
import ru.androidschool.intensiv.domain.entity.MovieDetail

object MovieDetailNetworkMapper : NetworkMapper<MovieDetail, MovieDetailResponse> {

    override fun map(from: MovieDetailResponse): MovieDetail {

        return MovieDetail(
            id = from.id ?: 0,
            title = from.title ?: "",
            posterPath = from.posterPath ?: "",
            voteAverage = from.voteAverage ?: 0.0,
            overview = from.overview ?: "",
            releaseDate = from.releaseDate.parseYearFromDate(),
            genres = from.genres?.map { it.name ?: "" } ?: emptyList(),
            productionCompanies = from.productionCompanies?.map { it.name ?: "" } ?: emptyList()
        )
    }
}
