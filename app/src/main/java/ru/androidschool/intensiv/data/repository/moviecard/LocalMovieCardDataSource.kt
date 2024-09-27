package ru.androidschool.intensiv.data.repository.moviecard

import io.reactivex.Completable
import io.reactivex.Single
import ru.androidschool.intensiv.domain.entity.MovieCard
import ru.androidschool.intensiv.utils.MovieType

interface LocalMovieCardDataSource {
    fun getListMovieCard(category: MovieType): Single<List<MovieCard>>

    fun saveMovieCard(movieCard: MovieCard, category: MovieType): Completable
}
