package ru.androidschool.intensiv.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import ru.androidschool.intensiv.domain.SearchRepository
import ru.androidschool.intensiv.domain.entity.MovieCard
import ru.androidschool.intensiv.ui.common.DataState
import ru.androidschool.intensiv.utils.extensions.applySchedulers
import timber.log.Timber

class SearchViewModel(
    private val searchRepository: SearchRepository
) : ViewModel() {

    private val _searchResults = MutableLiveData<DataState<List<MovieCard>>>()
    val searchResults: LiveData<DataState<List<MovieCard>>>
        get() = _searchResults

    private val compositeDisposable = CompositeDisposable()

    fun searchMovies(query: String) {
        _searchResults.value = DataState.Loading
        compositeDisposable.add(
            searchRepository.getSearchMovie(query)
                .applySchedulers()
                .subscribe({ movies ->
                    if (movies.isNotEmpty()) {
                        _searchResults.value = DataState.Success(movies)
                    } else {
                        _searchResults.value = DataState.Empty
                    }
                }, { error ->
                    Timber.e(error, "Error searching movies")
                    _searchResults.value = DataState.Error(error)
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
