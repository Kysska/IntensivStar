package ru.androidschool.intensiv.data.repository.moviedetail

import io.reactivex.Completable
import ru.androidschool.intensiv.domain.entity.MovieDetail

interface LocalMovieDetailDataSource {
    fun saveMovieDetail(movieDetail: MovieDetail): Completable

    fun deleteMovieDetail(movieDetail: MovieDetail): Completable
}
