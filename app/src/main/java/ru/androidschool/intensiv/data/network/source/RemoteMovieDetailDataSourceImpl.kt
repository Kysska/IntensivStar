package ru.androidschool.intensiv.data.network.source

import io.reactivex.Single
import ru.androidschool.intensiv.data.network.MovieApiInterface
import ru.androidschool.intensiv.data.network.mapper.MovieDetailNetworkMapper
import ru.androidschool.intensiv.data.repository.moviedetail.RemoteMovieDetailDataSource
import ru.androidschool.intensiv.domain.entity.MovieDetail

class RemoteMovieDetailDataSourceImpl(
    private val movieApiInterface: MovieApiInterface,
    private val networkMapper: MovieDetailNetworkMapper
) : RemoteMovieDetailDataSource, BaseRemoteDataSource<MovieDetail>() {

    override fun fetchMovieDetail(id: Int): Single<MovieDetail> {
        return fetchData(
            call = { movieApiInterface.getMovieDetailById(id) },
            mapper = { response -> networkMapper.map(response) },
            emptyResult = MovieDetail(),
            tag = "MovieDetail"
        )
    }
}
