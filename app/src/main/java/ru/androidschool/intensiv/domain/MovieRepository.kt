package ru.androidschool.intensiv.domain

import ru.androidschool.intensiv.data.network.util.CustomResult
import ru.androidschool.intensiv.domain.entity.MovieCard

interface MovieRepository {

    fun getMovies(callback: (CustomResult<List<MovieCard>>) -> Unit)
}
