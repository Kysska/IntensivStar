package ru.androidschool.intensiv.domain

import io.reactivex.Completable
import io.reactivex.Single
import ru.androidschool.intensiv.domain.entity.MovieCard
import ru.androidschool.intensiv.utils.MovieType

interface MovieRepository {
    fun getMovies(category: MovieType): Single<List<MovieCard>>

    fun saveMovie(movieCard: MovieCard, category: MovieType): Completable
}
