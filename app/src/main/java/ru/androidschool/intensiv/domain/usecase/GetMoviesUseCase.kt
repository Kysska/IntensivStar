package ru.androidschool.intensiv.domain.usecase

import io.reactivex.Single
import ru.androidschool.intensiv.domain.MovieRepository
import ru.androidschool.intensiv.domain.entity.MovieCard
import ru.androidschool.intensiv.utils.MovieType

class GetMoviesUseCase(
    private val movieRepository: MovieRepository
) {
    fun execute(category: MovieType): Single<List<MovieCard>> {
        return movieRepository.getMovies(category)
    }
}