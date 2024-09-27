package ru.androidschool.intensiv.ui

import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import ru.androidschool.intensiv.data.local.database.MovieDatabase
import ru.androidschool.intensiv.data.local.mapper.CastDatabaseMapper
import ru.androidschool.intensiv.data.local.mapper.MovieCardDatabaseMapper
import ru.androidschool.intensiv.data.local.mapper.MovieDetailDatabaseMapper
import ru.androidschool.intensiv.data.local.mapper.MovieWithCastMapper
import ru.androidschool.intensiv.data.local.source.LocalCastDataSourceImpl
import ru.androidschool.intensiv.data.local.source.LocalMovieCardDataSourceImpl
import ru.androidschool.intensiv.data.local.source.LocalMovieDetailDataSourceImpl
import ru.androidschool.intensiv.data.local.source.LocalMovieWithCastDataSourceImpl
import ru.androidschool.intensiv.data.network.MovieApiClient
import ru.androidschool.intensiv.data.network.mapper.CastCardNetworkMapper
import ru.androidschool.intensiv.data.network.mapper.MovieCardNetworkMapper
import ru.androidschool.intensiv.data.network.mapper.MovieDetailNetworkMapper
import ru.androidschool.intensiv.data.network.source.RemoteCastDataSourceImpl
import ru.androidschool.intensiv.data.network.source.RemoteMovieCardDataSourceImpl
import ru.androidschool.intensiv.data.network.source.RemoteMovieDetailDataSourceImpl
import ru.androidschool.intensiv.data.network.source.RemoteMovieWithCastDataSourceImpl
import ru.androidschool.intensiv.data.repository.cast.CastRepositoryImpl
import ru.androidschool.intensiv.data.repository.FavoriteMovieRepositoryImpl
import ru.androidschool.intensiv.data.repository.moviedetail.MovieDetailRepositoryImpl
import ru.androidschool.intensiv.data.repository.moviewithcast.MovieWithCastRepositoryImpl
import ru.androidschool.intensiv.data.repository.SearchRepositoryImpl
import ru.androidschool.intensiv.data.repository.cast.LocalCastDataSource
import ru.androidschool.intensiv.data.repository.cast.RemoteCastDataSource
import ru.androidschool.intensiv.data.repository.moviecard.LocalMovieCardDataSource
import ru.androidschool.intensiv.data.repository.moviecard.MovieCardRepositoryImpl
import ru.androidschool.intensiv.data.repository.moviecard.RemoteMovieCardDataSource
import ru.androidschool.intensiv.data.repository.moviedetail.LocalMovieDetailDataSource
import ru.androidschool.intensiv.data.repository.moviedetail.RemoteMovieDetailDataSource
import ru.androidschool.intensiv.data.repository.moviewithcast.LocalMovieWithCastDataSource
import ru.androidschool.intensiv.data.repository.moviewithcast.RemoteMovieWithCastDataSource
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

    protected val localMovieDetailDataSource: LocalMovieDetailDataSource by lazy {
        LocalMovieDetailDataSourceImpl(
            movieDatabase.movieDetailDao(),
            MovieDetailDatabaseMapper
        )
    }

    protected val remoteMovieDetailDataSource: RemoteMovieDetailDataSource by lazy {
        RemoteMovieDetailDataSourceImpl(
            MovieApiClient.apiClient,
            MovieDetailNetworkMapper
        )
    }


    protected val movieDetailRepositoryImpl: MovieDetailRepository by lazy {
        MovieDetailRepositoryImpl(
            localMovieDetailDataSource,
            remoteMovieDetailDataSource
        )
    }

    protected val localMovieCardDataSource: LocalMovieCardDataSource by lazy {
        LocalMovieCardDataSourceImpl(
            movieDatabase.movieCardDao(),
            MovieCardDatabaseMapper
        )
    }

    protected val remoteMovieCardDataSource: RemoteMovieCardDataSource by lazy {
        RemoteMovieCardDataSourceImpl(
            MovieApiClient.apiClient,
            MovieCardNetworkMapper
        )
    }


    protected val movieCardRepositoryImpl: MovieRepository by lazy {
        MovieCardRepositoryImpl(
            remoteMovieCardDataSource,
            localMovieCardDataSource
        )
    }

    protected val localCastDataSource: LocalCastDataSource by lazy {
        LocalCastDataSourceImpl(
            movieDatabase.movieDetailDao(),
            CastDatabaseMapper
        )
    }

    protected val remoteCastDataSource: RemoteCastDataSource by lazy {
        RemoteCastDataSourceImpl(
            MovieApiClient.apiClient,
            CastCardNetworkMapper
        )
    }

    protected val castRepositoryImpl: CastRepository by lazy {
        CastRepositoryImpl(
            remoteCastDataSource,
            localCastDataSource
        )
    }

    protected val localMovieWithCastDataSource: LocalMovieWithCastDataSource by lazy {
        LocalMovieWithCastDataSourceImpl(
            localMovieDetailDataSource,
            localCastDataSource,
            movieDatabase.movieDetailDao(),
            MovieWithCastMapper
        )
    }

    protected val remoteMovieWithCastDataSource: RemoteMovieWithCastDataSource by lazy {
        RemoteMovieWithCastDataSourceImpl(
            remoteMovieDetailDataSource,
            remoteCastDataSource
        )
    }

    protected val movieWithCastRepositoryImpl: MovieWithCastRepository by lazy {
        MovieWithCastRepositoryImpl(
            remoteMovieWithCastDataSource,
            localMovieWithCastDataSource
        )
    }

    protected val favoriteMovieRepository: FavoriteMovieRepository by lazy {
        FavoriteMovieRepositoryImpl(
            movieDatabase.movieDetailDao(),
            MovieWithCastMapper
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
