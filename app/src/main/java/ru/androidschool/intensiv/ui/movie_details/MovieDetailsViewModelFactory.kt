package ru.androidschool.intensiv.ui.movie_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.androidschool.intensiv.domain.MovieWithCastRepository

class MovieDetailsViewModelFactory(
    private val movieWithCastRepository: MovieWithCastRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieDetailsViewModel::class.java)) {
            return MovieDetailsViewModel(movieWithCastRepository) as T
        }
        throw IllegalArgumentException("Unknown viewModel")
    }
}
