package ru.androidschool.intensiv.ui.movie_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import ru.androidschool.intensiv.databinding.MovieDetailsFragmentBinding
import ru.androidschool.intensiv.domain.entity.CastCard
import ru.androidschool.intensiv.domain.entity.MovieDetail
import ru.androidschool.intensiv.domain.entity.MovieWithCast
import ru.androidschool.intensiv.ui.BaseFragment
import ru.androidschool.intensiv.ui.common.DataState
import ru.androidschool.intensiv.ui.feed.FeedFragment
import ru.androidschool.intensiv.utils.extensions.loadImage
import timber.log.Timber

class MovieDetailsFragment : BaseFragment() {

    private var _binding: MovieDetailsFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MovieDetailsViewModel by viewModels {
        MovieDetailsViewModelFactory(movieWithCastRepositoryImpl)
    }

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MovieDetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieId = getMovieId()

        observeViewModel()

        viewModel.fetchMovieWithCast(movieId)
    }

    private fun observeViewModel() {
        viewModel.movieDetails.observe(viewLifecycleOwner) { state ->
            when (state) {
                is DataState.Loading -> {
                    binding.progressBarContainer.progressBar.visibility = View.VISIBLE
                }

                is DataState.Success -> {
                    binding.progressBarContainer.progressBar.visibility = View.GONE
                    updateMovieDetailUi(state.data.movie)
                    updateCastListUI(state.data.cast)

                    observeFavoriteStatus(movieWithCast = state.data)
                }

                is DataState.Error -> {
                    Timber.e(state.exception, "Error loading movie details")
                    binding.progressBarContainer.progressBar.visibility = View.GONE
                }

                is DataState.Empty -> {
                    binding.progressBarContainer.progressBar.visibility = View.GONE
                    Timber.d("No result")
                }
            }
        }
    }

    private fun observeFavoriteStatus(movieWithCast: MovieWithCast) {
        viewModel.isFavorite.observe(viewLifecycleOwner) { isFavorite ->
            binding.favoriteCheckBox.isChecked = isFavorite
            onFavoriteCheckboxChanged(movieWithCast, isFavorite)
        }
    }

    private fun getMovieId(): Int {
        return requireArguments().getInt(FeedFragment.KEY_ID)
    }

    private fun updateMovieDetailUi(movie: MovieDetail) {
        binding.apply {
            movieTitleTextView.text = movie.title
            movieDetailsTextView.text = movie.overview
            movieRating.rating = movie.rating
            textViewStudio.text = movie.productionCompanies.joinToString(", ")
            textViewGenre.text = movie.genres.joinToString(", ")
            textViewYear.text = movie.releaseDate

            moviePosterImageView.loadImage(movie.posterPath)
        }
    }

    private fun updateCastListUI(castList: List<CastCard>) {
        val castListItem = castList.map {
            CastItem(it)
        }.toList()
        binding.recyclerView.adapter = adapter.apply { addAll(castListItem) }
    }

    private fun onFavoriteCheckboxChanged(movieWithCast: MovieWithCast, isFavorite: Boolean) {
        binding.favoriteCheckBox.isChecked = isFavorite

        binding.favoriteCheckBox.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onFavoriteCheckboxChanged(movieWithCast, isChecked)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val DEFAULT_CHECKBOX_VALUE = false
    }
}
