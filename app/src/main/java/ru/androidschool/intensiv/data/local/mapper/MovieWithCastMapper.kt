package ru.androidschool.intensiv.data.local.mapper

import ru.androidschool.intensiv.data.local.dto.MovieWithCastDb
import ru.androidschool.intensiv.domain.entity.MovieWithCast

object MovieWithCastMapper : DatabaseMapper<MovieWithCast, MovieWithCastDb> {
    override fun map(from: MovieWithCast): MovieWithCastDb {
        return MovieWithCastDb(
            movie = MovieDetailDatabaseMapper.map(from.movie),
            cast = CastDatabaseMapper.map(from.cast)
        )
    }

    override fun reverseMap(to: MovieWithCastDb): MovieWithCast {
        return MovieWithCast(
            movie = MovieDetailDatabaseMapper.reverseMap(to.movie),
            cast = CastDatabaseMapper.reverseMap(to.cast)
        )
    }
}
