package ru.androidschool.intensiv.ui.tvshows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import ru.androidschool.intensiv.databinding.TvShowsFragmentBinding
import ru.androidschool.intensiv.domain.entity.MovieCard
import ru.androidschool.intensiv.domain.usecase.GetMoviesUseCase
import ru.androidschool.intensiv.ui.BaseFragment

class TvShowsFragment : BaseFragment(), TvShowPresenter.TvShowView {

    private var _binding: TvShowsFragmentBinding? = null
    private val binding get() = _binding!!

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    private val getMoviesUseCase: GetMoviesUseCase by lazy {
        GetMoviesUseCase(movieCardRepositoryImpl)
    }

    private val presenter: TvShowPresenter by lazy {
        TvShowPresenter(getMoviesUseCase, this)
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

        binding.tvshowRecyclerView.adapter = adapter
        presenter.loadTvShow()
    }

    override fun showTvShows(tvShow: List<MovieCard>) {
        updateTvShowList(tvShow)
    }

    override fun showLoading() {
        binding.progressBarContainer.progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding.progressBarContainer.progressBar.visibility = View.GONE
    }

    override fun showError() {
        Toast.makeText(requireContext(), "Error loading TV shows", Toast.LENGTH_SHORT).show()
    }

    override fun showEmptyMovies() {
        Toast.makeText(requireContext(), "No TV shows available", Toast.LENGTH_SHORT).show()
    }

    private fun updateTvShowList(tvShowList: List<MovieCard>) {
        val tvShowListItem = tvShowList.map {
            TvShowsItem(it) {}
        }.toList()

        adapter.apply { addAll(tvShowListItem) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        presenter.clear()
    }
}
