package ru.androidschool.intensiv.ui.movie_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.network.MovieApiClient
import ru.androidschool.intensiv.data.repository.CastRepositoryImpl
import ru.androidschool.intensiv.data.repository.MovieDetailRepositoryImpl
import ru.androidschool.intensiv.databinding.MovieDetailsFragmentBinding
import ru.androidschool.intensiv.domain.CastRepository
import ru.androidschool.intensiv.domain.MovieDetailRepository
import ru.androidschool.intensiv.domain.entity.CastCard
import ru.androidschool.intensiv.domain.entity.MovieDetail
import ru.androidschool.intensiv.ui.feed.FeedFragment
import timber.log.Timber

class MovieDetailsFragment : Fragment(R.layout.movie_details_fragment) {

    private var _binding: MovieDetailsFragmentBinding? = null
    private val binding get() = _binding!!

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    private val compositeDisposable by lazy {
        CompositeDisposable()
    }

    private val movieDetailRepositoryImpl: MovieDetailRepository by lazy {
        MovieDetailRepositoryImpl(MovieApiClient.apiClient)
    }

    private val castRepositoryImpl: CastRepository by lazy {
        CastRepositoryImpl(MovieApiClient.apiClient)
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

        loadMovieDetailData(movieId)
        loadCastList(movieId)
    }

    private fun getMovieId(): Int {
        return requireArguments().getInt(FeedFragment.KEY_ID)
    }

    private fun loadMovieDetailData(id: Int) {
        compositeDisposable.add(
            movieDetailRepositoryImpl.getMovieDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ movie ->
                    updateMovieDetailUi(movie)
                }, { error ->
                    Timber.e(error, "Error loading movie detail")
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

            Picasso.get()
                .load(movie.posterPath)
                .into(moviePosterImageView)
        }
    }

    private fun loadCastList(id: Int) {
        compositeDisposable.add(
            castRepositoryImpl.getCasts(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ casts ->
                    updateCastListUI(casts)
                }, { error ->
                    Timber.e(error, "Error loading casts")
                })
        )
    }

    private fun updateCastListUI(castList: List<CastCard>) {
        val castListItem = castList.map {
            CastItem(it)
        }.toList()
        binding.recyclerView.adapter = adapter.apply { addAll(castListItem) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}
