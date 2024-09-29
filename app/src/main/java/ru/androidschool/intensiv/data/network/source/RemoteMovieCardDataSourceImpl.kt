package ru.androidschool.intensiv.data.network.source

import io.reactivex.Single
import ru.androidschool.intensiv.data.network.MovieApiInterface
import ru.androidschool.intensiv.data.network.mapper.MovieCardNetworkMapper
import ru.androidschool.intensiv.data.repository.moviecard.RemoteMovieCardDataSource
import ru.androidschool.intensiv.domain.entity.MovieCard
import ru.androidschool.intensiv.utils.MovieType

class RemoteMovieCardDataSourceImpl(
    private val movieApiInterface: MovieApiInterface,
    private val networkMapper: MovieCardNetworkMapper
) : RemoteMovieCardDataSource, BaseRemoteDataSource<List<MovieCard>>() {

    override fun fetchListMovieCard(category: MovieType): Single<List<MovieCard>> {
        return fetchData(
            call = { when (category) {
                MovieType.UPCOMING -> movieApiInterface.getUpcomingMovies()
                MovieType.POPULAR -> movieApiInterface.getPopularMovies()
                MovieType.NOW_PLAYING -> movieApiInterface.getNowPlayingMovies()
                MovieType.TV_SHOW -> movieApiInterface.getPopularTvShows()
                MovieType.TOP_RATED -> movieApiInterface.getTopRatedMovies()
            } },
            mapper = { response -> networkMapper.map(response.results ?: emptyList()) },
            emptyResult = emptyList(),
            tag = "MovieCard-${category.name}"
        )
    }
}
