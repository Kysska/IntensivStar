package ru.androidschool.intensiv.ui

import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import ru.androidschool.intensiv.data.local.database.MovieDatabase
import ru.androidschool.intensiv.data.local.mapper.CastDatabaseMapper
import ru.androidschool.intensiv.data.local.mapper.MovieCardDatabaseMapper
import ru.androidschool.intensiv.data.local.mapper.MovieDetailDatabaseMapper
import ru.androidschool.intensiv.data.local.mapper.MovieWithCastMapper
import ru.androidschool.intensiv.data.network.MovieApiClient
import ru.androidschool.intensiv.data.network.mapper.CastCardNetworkMapper
import ru.androidschool.intensiv.data.network.mapper.MovieCardNetworkMapper
import ru.androidschool.intensiv.data.network.mapper.MovieDetailNetworkMapper
import ru.androidschool.intensiv.data.repository.CastRepositoryImpl
import ru.androidschool.intensiv.data.repository.FavoriteMovieRepositoryImpl
import ru.androidschool.intensiv.data.repository.MovieDetailRepositoryImpl
import ru.androidschool.intensiv.data.repository.MovieWithCastRepositoryImpl
import ru.androidschool.intensiv.data.repository.NowPlayingMovieRepositoryImpl
import ru.androidschool.intensiv.data.repository.PopularMovieRepositoryImpl
import ru.androidschool.intensiv.data.repository.SearchRepositoryImpl
import ru.androidschool.intensiv.data.repository.TvShowRepositoryImpl
import ru.androidschool.intensiv.data.repository.UpcomingMovieRepositoryImpl
import ru.androidschool.intensiv.domain.CastRepository
import ru.androidschool.intensiv.domain.FavoriteMovieRepository
import ru.androidschool.intensiv.domain.MovieDetailRepository
import ru.androidschool.intensiv.domain.MovieRepository
import ru.androidschool.intensiv.domain.MovieWithCastRepository
import ru.androidschool.intensiv.domain.SearchRepository

abstract class BaseFragment : Fragment() {

    protected val compositeDisposable = CompositeDisposable()

    protected val movieDatabase: MovieDatabase by lazy {
        MovieDatabase.invoke(requireContext())
    }

    protected val nowPlayingMovieRepositoryImpl: MovieRepository by lazy {
        NowPlayingMovieRepositoryImpl(
            MovieApiClient.apiClient,
            movieDatabase.movieCardDao(),
            MovieCardDatabaseMapper,
            MovieCardNetworkMapper
        )
    }

    protected val popularMovieRepositoryImpl: MovieRepository by lazy {
        PopularMovieRepositoryImpl(
            MovieApiClient.apiClient,
            movieDatabase.movieCardDao(),
            MovieCardDatabaseMapper,
            MovieCardNetworkMapper
        )
    }

    protected val upcomingMovieRepositoryImpl: MovieRepository by lazy {
        UpcomingMovieRepositoryImpl(
            MovieApiClient.apiClient,
            movieDatabase.movieCardDao(),
            MovieCardDatabaseMapper,
            MovieCardNetworkMapper
        )
    }

    protected val movieDetailRepositoryImpl: MovieDetailRepository by lazy {
        MovieDetailRepositoryImpl(
            MovieApiClient.apiClient,
            movieDatabase.movieDetailDao(),
            MovieDetailNetworkMapper,
            MovieDetailDatabaseMapper
        )
    }

    protected val castRepositoryImpl: CastRepository by lazy {
        CastRepositoryImpl(
            MovieApiClient.apiClient,
            movieDatabase.movieDetailDao(),
            CastDatabaseMapper,
            CastCardNetworkMapper
        )
    }

    protected val movieWithCastRepositoryImpl: MovieWithCastRepository by lazy {
        MovieWithCastRepositoryImpl(
            movieDetailRepositoryImpl,
            castRepositoryImpl,
            movieDatabase.movieDetailDao(),
            MovieWithCastMapper
        )
    }

    protected val favoriteMovieRepository: FavoriteMovieRepository by lazy {
        FavoriteMovieRepositoryImpl(
            movieDatabase.movieDetailDao(),
            MovieWithCastMapper
        )
    }

    protected val tvShowRepositoryImpl: MovieRepository by lazy {
        TvShowRepositoryImpl(
            MovieApiClient.apiClient,
            movieDatabase.movieCardDao(),
            MovieCardDatabaseMapper,
            MovieCardNetworkMapper
        )
    }

    protected val searchRepository: SearchRepository by lazy {
        SearchRepositoryImpl(MovieApiClient.apiClient, MovieCardNetworkMapper)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
    }
}
