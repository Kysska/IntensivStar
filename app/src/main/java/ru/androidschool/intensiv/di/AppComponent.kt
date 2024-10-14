package ru.androidschool.intensiv.di

import dagger.Component
import ru.androidschool.intensiv.MovieFinderApp
import ru.androidschool.intensiv.ui.feed.FeedFragment
import ru.androidschool.intensiv.ui.movie_details.MovieDetailsFragment
import ru.androidschool.intensiv.ui.search.SearchFragment
import ru.androidschool.intensiv.ui.tvshows.TvShowsFragment
import ru.androidschool.intensiv.ui.watchlist.WatchlistFragment
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DomainModule::class,
        AppModule::class,
        DatabaseModule::class,
        LocalDataModule::class,
        RemoteDataModule::class,
        RepositoryModule::class,
        ViewModelModule::class]
)
interface AppComponent {
    fun inject(app: MovieFinderApp)
    fun inject(feedFragment: FeedFragment)
    fun inject(movieDetailsFragment: MovieDetailsFragment)
    fun inject(searchFragment: SearchFragment)
    fun inject(tvShowsFragment: TvShowsFragment)
    fun inject(watchlistFragment: WatchlistFragment)
}
