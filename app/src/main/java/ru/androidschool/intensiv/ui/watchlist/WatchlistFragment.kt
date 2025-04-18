package ru.androidschool.intensiv.ui.watchlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import ru.androidschool.intensiv.MovieFinderApp
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.databinding.FragmentWatchlistBinding
import ru.androidschool.intensiv.domain.FavoriteMovieRepository
import ru.androidschool.intensiv.domain.entity.MovieDetail
import ru.androidschool.intensiv.domain.entity.MovieWithCast
import ru.androidschool.intensiv.ui.BaseFragment
import ru.androidschool.intensiv.ui.feed.FeedFragment.Companion.KEY_ID
import ru.androidschool.intensiv.utils.extensions.applyLoader
import ru.androidschool.intensiv.utils.extensions.applySchedulers
import timber.log.Timber
import javax.inject.Inject

class WatchlistFragment : BaseFragment() {

    private var _binding: FragmentWatchlistBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    @Inject
    lateinit var favoriteMovieRepository: FavoriteMovieRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity().application as MovieFinderApp).component.inject(this)

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
                .subscribe({ movieList ->
                    updateFavoriteMovieUi(movieList)
                }, { error ->
                    Timber.e(error, "Error loading favorite movie list")
                })
        )
    }

    private fun updateFavoriteMovieUi(movieList: List<MovieWithCast>) {
        val moviesListItem =
            movieList.map {
                MoviePreviewItem(
                    it.movie
                ) { movie -> openMovieDetails(it.movie) }
            }.toList()

        binding.moviesRecyclerView.adapter = adapter.apply { addAll(moviesListItem) }
    }

    private fun openMovieDetails(movie: MovieDetail) {
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
