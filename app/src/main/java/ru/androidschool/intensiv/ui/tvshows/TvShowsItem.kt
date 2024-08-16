package ru.androidschool.intensiv.ui.tvshows

import android.view.View
import com.xwray.groupie.viewbinding.BindableItem
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.databinding.ItemTvShowBinding
import ru.androidschool.intensiv.domain.entity.MovieCard
import ru.androidschool.intensiv.utils.extensions.loadImage

class TvShowsItem(
    private val content: MovieCard,
    private val onClick: (movieCard: MovieCard) -> Unit
) : BindableItem<ItemTvShowBinding>() {

    override fun bind(view: ItemTvShowBinding, position: Int) {
        view.description.text = content.title
        view.movieRating.rating = content.rating
        view.content.setOnClickListener {
            onClick.invoke(content)
        }

        view.imagePreview.loadImage(content.posterPath)
    }

    override fun getLayout(): Int = R.layout.item_tv_show

    override fun initializeViewBinding(view: View) = ItemTvShowBinding.bind(view)
}
