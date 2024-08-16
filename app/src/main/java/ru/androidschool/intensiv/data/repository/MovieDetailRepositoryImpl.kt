package ru.androidschool.intensiv.data.repository

import ru.androidschool.intensiv.data.network.MovieApiInterface
import ru.androidschool.intensiv.data.network.mapper.MovieDetailMapper
import ru.androidschool.intensiv.data.network.util.CustomResult
import ru.androidschool.intensiv.domain.MovieDetailRepository
import ru.androidschool.intensiv.domain.entity.MovieDetail

class MovieDetailRepositoryImpl(private val movieApiInterface: MovieApiInterface) : BaseRepository(), MovieDetailRepository {

    override fun getMovieDetail(id: Int, callback: (CustomResult<MovieDetail>) -> Unit) {
        handleResponse(
            call = movieApiInterface.getMovieDetailById(id),
            mapper = { response -> MovieDetailMapper.toViewObject(response) },
            callback = callback
        )
    }
}
