package ru.androidschool.intensiv.ui.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.androidschool.intensiv.domain.usecase.FeedUseCase

class FeedViewModelFactory(
    private val feedUseCase: FeedUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FeedViewModel::class.java)) {
            return FeedViewModel(feedUseCase) as T
        }
        throw IllegalArgumentException("Unknown viewModel")
    }
}
