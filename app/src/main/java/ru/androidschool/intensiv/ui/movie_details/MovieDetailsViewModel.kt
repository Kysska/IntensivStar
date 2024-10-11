package ru.androidschool.intensiv.ui.movie_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import ru.androidschool.intensiv.domain.MovieWithCastRepository
import ru.androidschool.intensiv.domain.entity.MovieWithCast
import ru.androidschool.intensiv.ui.common.DataState
import ru.androidschool.intensiv.utils.extensions.applySchedulers
import timber.log.Timber

class MovieDetailsViewModel(
    private val movieWithCastRepository: MovieWithCastRepository
) : ViewModel() {

    private val _movieDetails = MutableLiveData<DataState<MovieWithCast>>()
    val movieDetails: LiveData<DataState<MovieWithCast>>
        get() = _movieDetails

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean>
        get() = _isFavorite

    private val compositeDisposable = CompositeDisposable()

    fun fetchMovieWithCast(id: Int) {
        _movieDetails.value = DataState.Loading
        compositeDisposable.add(
            movieWithCastRepository.getMovieWithCast(id)
                .applySchedulers()
                .subscribe({ movieWithCast ->
                    _movieDetails.value = DataState.Success(movieWithCast)
                    checkFavoriteStatus(movieWithCast.movie.id)
                }, { error ->
                    _movieDetails.value = DataState.Error(error)
                })
        )
    }

    private fun checkFavoriteStatus(movieId: Int) {
        compositeDisposable.add(
            movieWithCastRepository.isMovieExists(movieId)
                .applySchedulers()
                .subscribe({ isFavorite ->
                    _isFavorite.value = isFavorite
                }, { error ->
                    Timber.e(error, "Error checking favorite status")
                    _isFavorite.value = false
                })
        )
    }

    fun onFavoriteCheckboxChanged(movieWithCast: MovieWithCast, isChecked: Boolean) {
        if (isChecked) {
            compositeDisposable.add(
                movieWithCastRepository.saveMovieWithCast(movieWithCast)
                    .applySchedulers()
                    .subscribe({
                        Timber.d("Movie saved to favorites")
                    }, { error ->
                        Timber.e(error, "Failed to save movie to favorites")
                        _isFavorite.value = false
                    })
            )
        } else {
            compositeDisposable.add(
                movieWithCastRepository.deleteMovieWithCast(movieWithCast)
                    .applySchedulers()
                    .subscribe({
                        Timber.d("Movie removed from favorites")
                    }, { error ->
                        Timber.e(error, "Failed to remove movie from favorites")
                        _isFavorite.value = true
                    })
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
