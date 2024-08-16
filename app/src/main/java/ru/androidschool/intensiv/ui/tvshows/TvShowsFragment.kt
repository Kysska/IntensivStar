package ru.androidschool.intensiv.ui.tvshows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.network.util.CustomResult
import ru.androidschool.intensiv.data.network.MovieApiClient
import ru.androidschool.intensiv.data.repository.TvShowRepositoryImpl
import ru.androidschool.intensiv.databinding.TvShowsFragmentBinding
import ru.androidschool.intensiv.domain.MovieRepository
import ru.androidschool.intensiv.domain.entity.MovieCard

class TvShowsFragment : Fragment(R.layout.tv_shows_fragment) {

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
        tvShowRepositoryImpl.getMovies {
            when (it) {
                is CustomResult.Loading -> {}
                is CustomResult.Success -> {
                    updateTvShowList(it.data)
                }
                is CustomResult.Error -> {}
            }
        }
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
