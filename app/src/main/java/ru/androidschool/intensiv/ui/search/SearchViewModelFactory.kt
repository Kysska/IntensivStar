package ru.androidschool.intensiv.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.androidschool.intensiv.domain.SearchRepository

class SearchViewModelFactory(
    private val searchRepository: SearchRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(searchRepository) as T
        }
        throw IllegalArgumentException("Unknown viewModel")
    }
}
