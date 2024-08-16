package ru.androidschool.intensiv.data.repository

import ru.androidschool.intensiv.data.network.util.CustomResult
import ru.androidschool.intensiv.data.network.MovieApiInterface
import ru.androidschool.intensiv.data.network.mapper.MovieCardMapper
import ru.androidschool.intensiv.domain.MovieRepository
import ru.androidschool.intensiv.domain.entity.MovieCard

class NowPlayingMovieRepositoryImpl(private val movieApiInterface: MovieApiInterface) : BaseRepository(), MovieRepository {

    override fun getMovies(callback: (CustomResult<List<MovieCard>>) -> Unit) {
        handleResponse(
            call = movieApiInterface.getNowPlayingMovies(),
            mapper = { response -> response.results?.map { MovieCardMapper.toViewObject(it) } ?: emptyList() },
            callback = callback
        )
    }
}
