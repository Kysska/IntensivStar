package ru.androidschool.intensiv.domain

import io.reactivex.Completable
import io.reactivex.Single
import ru.androidschool.intensiv.domain.entity.MovieDetail

interface MovieDetailRepository {
    fun getMovieDetail(id: Int): Single<MovieDetail>

    fun saveMovieDetail(movieDetail: MovieDetail): Completable

    fun deleteMovieDetail(movieDetail: MovieDetail): Completable
}
