package ru.androidschool.intensiv.data.network.mapper

import ru.androidschool.intensiv.utils.extensions.parseYearFromDate
import ru.androidschool.intensiv.data.network.dto.MovieDetailResponse
import ru.androidschool.intensiv.domain.entity.MovieDetail

object MovieDetailMapper : ViewObjectMapper<MovieDetail, MovieDetailResponse> {

    override fun toViewObject(dto: MovieDetailResponse): MovieDetail {

        return MovieDetail(
            id = dto.id ?: 0,
            title = dto.title ?: "",
            posterPath = dto.posterPath ?: "",
            voteAverage = dto.voteAverage ?: 0.0,
            overview = dto.overview ?: "",
            releaseDate = dto.releaseDate.parseYearFromDate(),
            genres = dto.genres?.map { it.name ?: "" } ?: emptyList(),
            productionCompanies = dto.productionCompanies?.map { it.name ?: "" } ?: emptyList()
        )
    }
}
