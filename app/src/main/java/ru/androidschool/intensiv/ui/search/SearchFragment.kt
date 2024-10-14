package ru.androidschool.intensiv.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import ru.androidschool.intensiv.MovieFinderApp
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.databinding.FeedHeaderBinding
import ru.androidschool.intensiv.databinding.FragmentSearchBinding
import ru.androidschool.intensiv.domain.entity.MovieCard
import ru.androidschool.intensiv.ui.BaseFragment
import ru.androidschool.intensiv.ui.common.DataState
import ru.androidschool.intensiv.ui.common.OnBackButtonClickListener
import ru.androidschool.intensiv.ui.feed.FeedFragment.Companion.KEY_ID
import ru.androidschool.intensiv.ui.feed.FeedFragment.Companion.KEY_SEARCH
import ru.androidschool.intensiv.ui.feed.MovieItem
import timber.log.Timber
import javax.inject.Inject

class SearchFragment : BaseFragment(), OnBackButtonClickListener {

    private var _binding: FragmentSearchBinding? = null
    private var _searchBinding: FeedHeaderBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val searchBinding get() = _searchBinding!!

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity().application as MovieFinderApp).component.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SearchViewModel::class.java)

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        _searchBinding = FeedHeaderBinding.bind(binding.root)

        searchBinding.searchToolbar.setOnClearButtonClickListener(this)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val searchTerm = getKeySearch() ?: ""
        searchBinding.searchToolbar.setText(searchTerm)

        binding.moviesRecyclerView.layoutManager = GridLayoutManager(context, GRID_SPAN_COUNT)
        binding.moviesRecyclerView.adapter = adapter

        observeViewModel()

        if (searchTerm.isNotEmpty()) {
            viewModel.searchMovies(searchTerm)
        }
    }

    private fun observeViewModel() {
        viewModel.searchResults.observe(viewLifecycleOwner) { state ->
            when (state) {
                is DataState.Loading -> {
                    binding.progressBarContainer.progressBar.visibility = View.VISIBLE
                }

                is DataState.Success -> {
                    binding.progressBarContainer.progressBar.visibility = View.GONE
                    displaySearchResults(state.data)
                }

                is DataState.Error -> {
                    binding.progressBarContainer.progressBar.visibility = View.GONE
                    Timber.e(state.exception, "Error searching movies")
                }

                is DataState.Empty -> {
                    binding.progressBarContainer.progressBar.visibility = View.GONE
                    Timber.d("No search result")
                }
            }
        }
    }

    private fun getKeySearch(): String? {
        return requireArguments().getString(KEY_SEARCH)
    }

    private fun displaySearchResults(movies: List<MovieCard>) {
        val movieItems = movies.map { movie ->
            MovieItem(movie) {
                openMovieDetails(it)
            }
        }
        adapter.update(movieItems)
    }

    private fun openMovieDetails(movie: MovieCard) {
        val bundle = Bundle()
        bundle.putInt(KEY_ID, movie.id)
        findNavController().navigate(R.id.movie_details_fragment, bundle)
    }

    override fun onClearButtonClicked() {
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _searchBinding = null
    }

    companion object {
        private const val GRID_SPAN_COUNT = 3
    }
}
