package ru.androidschool.intensiv.domain

import io.reactivex.Single
import ru.androidschool.intensiv.domain.entity.MovieCard

interface SearchRepository {
    fun getSearchMovie(query: String): Single<List<MovieCard>>
}
