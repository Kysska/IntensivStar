package ru.androidschool.intensiv.domain

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import ru.androidschool.intensiv.domain.entity.MovieCard

interface FavoriteMovieRepository {

    fun getAllFavoriteMovieList(): Observable<List<MovieCard>>

    fun getIsMovieFavorite(movieId: Int): Maybe<Boolean>

    fun setIsMovieFavorite(movieId: Int, isFavorite: Boolean): Completable
}
