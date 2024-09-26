package ru.androidschool.intensiv.domain

import io.reactivex.Completable
import io.reactivex.Single
import ru.androidschool.intensiv.domain.entity.MovieWithCast

interface MovieWithCastRepository {

    fun getMovieWithCastFromNetwork(id: Int): Single<MovieWithCast>

    fun getMovieWithCastFromLocal(id: Int): Single<MovieWithCast>

    fun saveMovieWithCast(movieWithCast: MovieWithCast): Completable

    fun deleteMovieWithCast(movieWithCast: MovieWithCast): Completable

    fun isMovieExists(id: Int): Single<Boolean>
}
