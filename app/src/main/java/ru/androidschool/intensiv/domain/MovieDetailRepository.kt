package ru.androidschool.intensiv.domain

import ru.androidschool.intensiv.data.network.util.CustomResult
import ru.androidschool.intensiv.domain.entity.MovieDetail

interface MovieDetailRepository {
    fun getMovieDetail(id: Int, callback: (CustomResult<MovieDetail>) -> Unit)
}
