package ru.androidschool.intensiv.domain

import io.reactivex.Completable
import io.reactivex.Single
import ru.androidschool.intensiv.domain.entity.MovieCard

interface MovieRepository {
    fun getMoviesFromNetwork(): Single<List<MovieCard>>

    fun getMoviesFromLocalByCategory(): Single<List<MovieCard>>

    fun saveMovie(movieCard: MovieCard): Completable
}
