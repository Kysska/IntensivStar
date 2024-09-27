package ru.androidschool.intensiv.data.repository.moviecard

import io.reactivex.Single
import ru.androidschool.intensiv.domain.entity.MovieCard
import ru.androidschool.intensiv.utils.MovieType

interface RemoteMovieCardDataSource {
    fun fetchListMovieCard(category: MovieType): Single<List<MovieCard>>
}
