package ru.androidschool.intensiv.domain

import io.reactivex.Single
import ru.androidschool.intensiv.domain.entity.MovieDetail

interface MovieDetailRepository {
    fun getMovieDetail(id: Int): Single<MovieDetail>
}
