package ru.androidschool.intensiv.ui.feed

import android.os.Bundle
import android.view.*
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.databinding.FeedFragmentBinding
import ru.androidschool.intensiv.databinding.FeedHeaderBinding
import ru.androidschool.intensiv.domain.entity.MovieCard
import ru.androidschool.intensiv.domain.usecase.GetMoviesUseCase
import ru.androidschool.intensiv.domain.usecase.FeedUseCase
import ru.androidschool.intensiv.ui.BaseFragment
import ru.androidschool.intensiv.utils.MovieType
import ru.androidschool.intensiv.utils.extensions.applyLoader
import ru.androidschool.intensiv.utils.extensions.applySchedulers
import timber.log.Timber

class FeedFragment : BaseFragment() {

    private var _binding: FeedFragmentBinding? = null
    private var _searchBinding: FeedHeaderBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val searchBinding get() = _searchBinding!!

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    private val getMoviesUseCase: GetMoviesUseCase by lazy {
        GetMoviesUseCase(movieCardRepositoryImpl)
    }

    private val feedUseCase: FeedUseCase by lazy {
        FeedUseCase(getMoviesUseCase)
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

        setupSearchObserver()

        binding.moviesRecyclerView.adapter = adapter
        adapter.clear()

        loadMovies()
    }

    private fun loadMovies() {
        compositeDisposable.add(
            feedUseCase.execute()
                .applySchedulers()
                .applyLoader(binding.progressBarContainer.progressBar)
                .subscribe({ moviesMap ->
                    moviesMap.forEach { (movieType, movies) ->
                        updateMovieCardList(movies, movieType)
                    }
                }, { error ->
                    Timber.e(error, "Error loading movies")
                })
        )
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
                MovieType.TOP_RATED -> R.string.top_rated
                MovieType.TV_SHOW -> R.string.tv_show
            },
            items = movieItems
        )

        adapter.add(mainCardContainer)
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

    private fun setupSearchObserver() {
        compositeDisposable.add(
            searchBinding.searchToolbar.observeSearchTextWithFilter()
                .subscribe(
                    { searchText -> openSearch(searchText) },
                    { error -> Timber.e(error, "Error in search") }
                )
        )
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

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    companion object {
        const val KEY_ID = "id"
        const val KEY_SEARCH = "search"
    }
}
