package ru.androidschool.intensiv.ui.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import ru.androidschool.intensiv.domain.entity.MovieCard
import ru.androidschool.intensiv.domain.usecase.FeedUseCase
import ru.androidschool.intensiv.ui.common.DataState
import ru.androidschool.intensiv.utils.MovieType
import ru.androidschool.intensiv.utils.extensions.applySchedulers
import timber.log.Timber

class FeedViewModel(
    private val feedUseCase: FeedUseCase
) : ViewModel() {

    private val _moviesState = MutableLiveData<DataState<Map<MovieType, List<MovieCard>>>>()
    val moviesState: LiveData<DataState<Map<MovieType, List<MovieCard>>>>
        get() = _moviesState

    private val compositeDisposable = CompositeDisposable()

    fun loadMovies() {
        _moviesState.value = DataState.Loading
        compositeDisposable.add(
            feedUseCase.execute()
                .applySchedulers()
                .subscribe({ movies ->
                    if (movies.isNotEmpty()) {
                        _moviesState.value = DataState.Success(movies)
                    } else {
                        _moviesState.value = DataState.Empty
                    }
                }, { error ->
                    Timber.e(error, "Error loading movies")
                    _moviesState.value = DataState.Error(error)
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
