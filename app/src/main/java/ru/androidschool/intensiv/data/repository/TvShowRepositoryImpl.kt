package ru.androidschool.intensiv.data.repository

import io.reactivex.Single
import ru.androidschool.intensiv.data.network.MovieApiInterface
import ru.androidschool.intensiv.data.network.mapper.MovieCardMapper
import ru.androidschool.intensiv.domain.MovieRepository
import ru.androidschool.intensiv.domain.entity.MovieCard

class TvShowRepositoryImpl(private val movieApiInterface: MovieApiInterface)
    : BaseRepository<List<MovieCard>>(), MovieRepository {

    override fun getMovies(): Single<List<MovieCard>> {
        return fetchData(
            apiCall = { movieApiInterface.getPopularTvShows() },
            mapper = { response -> MovieCardMapper.toViewObject(response.results ?: emptyList()) },
            emptyResult = emptyList()
        )
    }
}
