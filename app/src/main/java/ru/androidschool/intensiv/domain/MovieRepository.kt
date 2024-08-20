package ru.androidschool.intensiv.domain

import io.reactivex.Single
import ru.androidschool.intensiv.domain.entity.MovieCard

interface MovieRepository {
    fun getMovies(): Single<List<MovieCard>>
}
