package ru.androidschool.intensiv.data.repository

import io.reactivex.Single
import ru.androidschool.intensiv.data.network.MovieApiInterface
import ru.androidschool.intensiv.data.network.mapper.MovieDetailMapper
import ru.androidschool.intensiv.domain.MovieDetailRepository
import ru.androidschool.intensiv.domain.entity.MovieDetail

class MovieDetailRepositoryImpl(private val movieApiInterface: MovieApiInterface) :
    BaseRepository<MovieDetail>(), MovieDetailRepository {

    override fun getMovieDetail(id: Int): Single<MovieDetail> {
        return fetchData(
            apiCall = { movieApiInterface.getMovieDetailById(id) },
            mapper = { response -> MovieDetailMapper.toViewObject(response) },
            emptyResult = MovieDetail()
        )
    }
}
