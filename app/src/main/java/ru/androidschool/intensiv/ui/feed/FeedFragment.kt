package ru.androidschool.intensiv.ui.feed

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.network.util.CustomResult
import ru.androidschool.intensiv.data.network.MovieApiClient
import ru.androidschool.intensiv.data.repository.NowPlayingMovieRepositoryImpl
import ru.androidschool.intensiv.data.repository.PopularMovieRepositoryImpl
import ru.androidschool.intensiv.data.repository.UpcomingMovieRepositoryImpl
import ru.androidschool.intensiv.databinding.FeedFragmentBinding
import ru.androidschool.intensiv.databinding.FeedHeaderBinding
import ru.androidschool.intensiv.domain.MovieRepository
import ru.androidschool.intensiv.domain.entity.MovieCard
import ru.androidschool.intensiv.utils.MovieType
import ru.androidschool.intensiv.utils.extensions.afterTextChanged
import timber.log.Timber

class FeedFragment : Fragment(R.layout.feed_fragment) {

    private var _binding: FeedFragmentBinding? = null
    private var _searchBinding: FeedHeaderBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val searchBinding get() = _searchBinding!!

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    private val nowPlayingMovieRepositoryImpl: MovieRepository by lazy {
        NowPlayingMovieRepositoryImpl(MovieApiClient.apiClient)
    }

    private val popularMovieRepositoryImpl: MovieRepository by lazy {
        PopularMovieRepositoryImpl(MovieApiClient.apiClient)
    }

    private val upcomingMovieRepositoryImpl: MovieRepository by lazy {
        UpcomingMovieRepositoryImpl(MovieApiClient.apiClient)
    }

    private val options = navOptions {
        anim {
            enter = R.anim.slide_in_right
            exit = R.anim.slide_out_left
            popEnter = R.anim.slide_in_left
            popExit = R.anim.slide_out_right
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FeedFragmentBinding.inflate(inflater, container, false)
        _searchBinding = FeedHeaderBinding.bind(binding.root)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchBinding.searchToolbar.binding.searchEditText.afterTextChanged {
            Timber.d(it.toString())
            if (it.toString().length > MIN_LENGTH) {
                openSearch(it.toString())
            }
        }

        loadNowPlayingMovies()
        loadPopularMovies()
        loadUpcomingMovies()
    }

    private fun loadNowPlayingMovies() {
        nowPlayingMovieRepositoryImpl.getMovies {
            when (it) {
                is CustomResult.Loading -> {}
                is CustomResult.Success -> {
                    updateMovieCardList(it.data, MovieType.NOW_PLAYING)
                }
                is CustomResult.Error -> {}
            }
        }
    }

    private fun loadPopularMovies() {
        popularMovieRepositoryImpl.getMovies {
            when (it) {
                is CustomResult.Loading -> {}
                is CustomResult.Success -> {
                    updateMovieCardList(it.data, MovieType.POPULAR)
                }
                is CustomResult.Error -> {}
            }
        }
    }

    private fun loadUpcomingMovies() {
        upcomingMovieRepositoryImpl.getMovies {
            when (it) {
                is CustomResult.Loading -> {}
                is CustomResult.Success -> {
                    updateMovieCardList(it.data, MovieType.UPCOMING)
                }
                is CustomResult.Error -> {}
            }
        }
    }

    private fun updateMovieCardList(moviesList: List<MovieCard>, movieType: MovieType) {
        val movieItems = moviesList.map { movie ->
            MovieItem(movie) { clickedMovie ->
                openMovieDetails(clickedMovie)
            }
        }

        val mainCardContainer = MainCardContainer(
            title = when (movieType) {
                MovieType.NOW_PLAYING -> R.string.recommended
                MovieType.POPULAR -> R.string.popular
                MovieType.UPCOMING -> R.string.upcoming
            },
            items = movieItems
        )

        adapter.add(mainCardContainer)
        binding.moviesRecyclerView.adapter = adapter
    }

    private fun openMovieDetails(movie: MovieCard) {
        val bundle = Bundle()
        bundle.putInt(KEY_ID, movie.id)
        findNavController().navigate(R.id.movie_details_fragment, bundle, options)
    }

    private fun openSearch(searchText: String) {
        val bundle = Bundle()
        bundle.putString(KEY_SEARCH, searchText)
        findNavController().navigate(R.id.search_dest, bundle, options)
    }

    override fun onStop() {
        super.onStop()
        searchBinding.searchToolbar.clear()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _searchBinding = null
    }

    companion object {
        const val MIN_LENGTH = 3
        const val KEY_TITLE = "title"
        const val KEY_ID = "id"
        const val KEY_SEARCH = "search"
    }
}
