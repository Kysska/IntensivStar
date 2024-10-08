package ru.androidschool.intensiv.ui.tvshows

import io.reactivex.disposables.CompositeDisposable
import ru.androidschool.intensiv.domain.entity.MovieCard
import ru.androidschool.intensiv.domain.usecase.GetMoviesUseCase
import ru.androidschool.intensiv.utils.MovieType
import ru.androidschool.intensiv.utils.extensions.applySchedulers
import timber.log.Timber

class TvShowPresenter(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val view: TvShowView
) {

    private val compositeDisposable = CompositeDisposable()

    fun loadTvShow() {
        compositeDisposable.add(
            getMoviesUseCase.execute(MovieType.TV_SHOW)
                .applySchedulers()
                .doOnSubscribe { view.showLoading() }
                .doFinally { view.hideLoading() }
                .subscribe({ movieList ->
                    if (movieList.isEmpty()) {
                        view.showEmptyMovies()
                    } else {
                        view.showTvShows(movieList)
                    }
                }, { error ->
                    view.showError()
                    Timber.e(error, "Error loading tv show")
                })
        )
    }

    fun clear() {
        compositeDisposable.clear()
    }

    interface TvShowView {
        fun showTvShows(tvShow: List<MovieCard>)
        fun showLoading()
        fun hideLoading()
        fun showError()
        fun showEmptyMovies()
    }
}
