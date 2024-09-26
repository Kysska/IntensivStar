package ru.androidschool.intensiv.domain

import io.reactivex.Observable
import ru.androidschool.intensiv.domain.entity.MovieWithCast

interface FavoriteMovieRepository {

    fun getAllFavoriteMovieList(): Observable<List<MovieWithCast>>
}
