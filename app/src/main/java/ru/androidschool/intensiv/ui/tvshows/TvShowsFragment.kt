package ru.androidschool.intensiv.ui.tvshows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import ru.androidschool.intensiv.data.network.MovieApiClient
import ru.androidschool.intensiv.data.repository.TvShowRepositoryImpl
import ru.androidschool.intensiv.databinding.TvShowsFragmentBinding
import ru.androidschool.intensiv.domain.MovieRepository
import ru.androidschool.intensiv.domain.entity.MovieCard
import ru.androidschool.intensiv.ui.BaseFragment
import ru.androidschool.intensiv.utils.extensions.applyLoader
import ru.androidschool.intensiv.utils.extensions.applySchedulers
import timber.log.Timber

class TvShowsFragment : BaseFragment() {

    private var _binding: TvShowsFragmentBinding? = null
    private val binding get() = _binding!!

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    private val tvShowRepositoryImpl: MovieRepository by lazy {
        TvShowRepositoryImpl(MovieApiClient.apiClient)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TvShowsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadNowPlayingMovies()
    }

    private fun loadNowPlayingMovies() {
        compositeDisposable.add(
            tvShowRepositoryImpl.getMovies()
                .applySchedulers()
                .applyLoader(binding.progressBarContainer.progressBar)
                .subscribe({ movies ->
                    updateTvShowList(movies)
                }, { error ->
                    Timber.e(error, "Error loading tv show")
                })
        )
    }

    private fun updateTvShowList(tvShowList: List<MovieCard>) {
        val tvShowListItem = tvShowList.map {
            TvShowsItem(it) {}
        }.toList()

        binding.tvshowRecyclerView.adapter = adapter.apply { addAll(tvShowListItem) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
