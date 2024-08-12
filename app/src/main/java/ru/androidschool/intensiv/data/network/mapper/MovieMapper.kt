package ru.androidschool.intensiv.data.network.mapper

import ru.androidschool.intensiv.data.network.dto.CastResponse
import ru.androidschool.intensiv.data.network.dto.MovieDetailResponse
import ru.androidschool.intensiv.data.network.dto.MovieResponse
import ru.androidschool.intensiv.domain.entity.CastCard
import ru.androidschool.intensiv.domain.entity.MovieCard
import ru.androidschool.intensiv.domain.entity.MovieDetail

object MovieMapper {

    fun mapToMovieCardEntity(movie: MovieResponse): MovieCard {
        return MovieCard(
            id = movie.id ?: 0,
            title = movie.title ?: "",
            posterPath = movie.posterPath ?: "",
            voteAverage = movie.voteAverage ?: 0.0
        )
    }

    fun mapToMovieDetailEntity(movie: MovieDetailResponse): MovieDetail {
        return MovieDetail(
            id = movie.id ?: 0,
            title = movie.title ?: "",
            posterPath = movie.posterPath ?: "",
            voteAverage = movie.voteAverage ?: 0.0,
            overview = movie.overview ?: "",
            releaseDate = movie.releaseDate?.substring(0, 4) ?: "",
            genres = movie.genres?.map { it.name ?: "" } ?: emptyList(),
            productionCompanies = movie.productionCompanies?.map { it.name ?: "" } ?: emptyList()
        )
    }

    fun mapToCastCardEntity(castResponse: CastResponse): CastCard {
        return CastCard(
            id = castResponse.castId ?: 0,
            name = castResponse.name ?: "",
            posterPath = castResponse.profilePath ?: ""
        )
    }
}
