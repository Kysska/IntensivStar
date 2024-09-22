package ru.androidschool.intensiv.ui.watchlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import io.reactivex.Observable
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.databinding.FragmentWatchlistBinding
import ru.androidschool.intensiv.domain.entity.MovieCard
import ru.androidschool.intensiv.ui.BaseFragment
import ru.androidschool.intensiv.ui.feed.FeedFragment.Companion.KEY_ID
import ru.androidschool.intensiv.utils.extensions.applyLoader
import ru.androidschool.intensiv.utils.extensions.applySchedulers
import timber.log.Timber

class WatchlistFragment : BaseFragment() {

    private var _binding: FragmentWatchlistBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWatchlistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.moviesRecyclerView.layoutManager = GridLayoutManager(context, 4)
        binding.moviesRecyclerView.adapter = adapter.apply { addAll(listOf()) }
        adapter.clear()

        loadLocalFavoriteMovie()
    }

    private fun loadLocalFavoriteMovie() {
        compositeDisposable.add(
            favoriteMovieRepository.getAllFavoriteMovieList()
                .applySchedulers()
                .applyLoader(binding.progressBarContainer.progressBar)
                .switchMap { data ->
                    if (data.isEmpty()) {
                        Observable.empty<List<MovieCard>>().doOnComplete { binding.progressBarContainer.progressBar.visibility = View.GONE }
                    } else {
                        Observable.just(data)
                    }
                }
                .subscribe({ movieList ->
                    updateFavoriteMovieUi(movieList)
                }, { error ->
                    Timber.e(error, "Error loading favorite movie list")
                })
        )
    }

    private fun updateFavoriteMovieUi(movieList: List<MovieCard>) {
        val moviesListItem =
            movieList.map {
                MoviePreviewItem(
                    it
                ) { movie -> openMovieDetails(it) }
            }.toList()

        binding.moviesRecyclerView.adapter = adapter.apply { addAll(moviesListItem) }
    }

    private fun openMovieDetails(movie: MovieCard) {
        val bundle = Bundle()
        bundle.putInt(KEY_ID, movie.id)
        findNavController().navigate(R.id.movie_details_fragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = WatchlistFragment()
    }
}
