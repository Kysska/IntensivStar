package ru.androidschool.intensiv.ui.movie_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import io.reactivex.Single
import ru.androidschool.intensiv.databinding.MovieDetailsFragmentBinding
import ru.androidschool.intensiv.domain.entity.CastCard
import ru.androidschool.intensiv.domain.entity.MovieDetail
import ru.androidschool.intensiv.ui.BaseFragment
import ru.androidschool.intensiv.ui.feed.FeedFragment
import ru.androidschool.intensiv.utils.extensions.applyLoader
import ru.androidschool.intensiv.utils.extensions.applySchedulers
import ru.androidschool.intensiv.utils.extensions.loadImage
import timber.log.Timber

class MovieDetailsFragment : BaseFragment() {

    private var _binding: MovieDetailsFragmentBinding? = null
    private val binding get() = _binding!!

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

        fetchMovieWithCastFromNetwork(movieId)
    }

    private fun getMovieId(): Int {
        return requireArguments().getInt(FeedFragment.KEY_ID)
    }

    private fun fetchMovieWithCastFromNetwork(id: Int) {
        compositeDisposable.add(
            movieWithCastRepositoryImpl.getMovieWithCastFromNetwork(id)
                .flatMap { movieWithCast ->
                    movieWithCastRepositoryImpl.saveMovieWithCast(movieWithCast)
                        .andThen(Single.just(movieWithCast))
                }
                .onErrorResumeNext { error ->
                    Timber.e(error, "Error movie from network")
                    movieWithCastRepositoryImpl.getMovieWithCastFromLocal(id)
                }
                .applySchedulers()
                .applyLoader(binding.progressBarContainer.progressBar)
                .subscribe({ movieWithCast ->
                    updateMovieDetailUi(movieWithCast.movie)
                    updateCastListUI(movieWithCast.cast)

                    checkFavoriteStatus(movieWithCast.movie.id)
                }, { networkError ->
                    Timber.e(networkError, "Error loading movie detail from network")
                })
        )
    }

    private fun checkFavoriteStatus(movieId: Int) {
        compositeDisposable.add(
            favoriteMovieRepository.getIsMovieFavorite(movieId)
                .applySchedulers()
                .applyLoader(binding.progressBarContainer.progressBar)
                .subscribe({ isFavorite ->
                    onFavoriteCheckboxChanged(movieId, isFavorite)
                }, { error ->
                    Timber.e(error, "Error favorite status")
                }, {
                    Timber.tag("favStatus").d("No favorite")
                    onFavoriteCheckboxChanged(movieId, DEFAULT_CHECKBOX_VALUE)
                })
        )
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

    private fun onFavoriteCheckboxChanged(movieId: Int, isFavorite: Boolean) {
        binding.favoriteCheckBox.isChecked = isFavorite

        binding.favoriteCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                compositeDisposable.add(
                    favoriteMovieRepository.setIsMovieFavorite(movieId, isChecked)
                        .applySchedulers()
                        .subscribe({
                            Timber.d("Saved to fav")
                        }, { error ->
                            Timber.e(error, "Failed to save movie")
                            binding.favoriteCheckBox.isChecked = false
                        })
                )
            } else {
                compositeDisposable.add(
                    favoriteMovieRepository.setIsMovieFavorite(movieId, isChecked)
                        .applySchedulers()
                        .subscribe({
                            Timber.d("Delete fav")
                        }, { error ->
                            Timber.e(error, "Failed to save movie")
                            binding.favoriteCheckBox.isChecked = true
                        })
                )
            }
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
