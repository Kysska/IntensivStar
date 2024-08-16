package ru.androidschool.intensiv.ui.feed

import android.view.View
import com.xwray.groupie.viewbinding.BindableItem
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.databinding.ItemWithTextBinding
import ru.androidschool.intensiv.domain.entity.MovieCard
import ru.androidschool.intensiv.utils.extensions.loadImage

class MovieItem(
    private val content: MovieCard,
    private val onClick: (movie: MovieCard) -> Unit
) : BindableItem<ItemWithTextBinding>() {

    override fun getLayout(): Int = R.layout.item_with_text

    override fun bind(view: ItemWithTextBinding, position: Int) {
        view.description.text = content.title
        view.movieRating.rating = content.rating
        view.content.setOnClickListener {
            onClick.invoke(content)
        }

        view.imagePreview.loadImage(content.posterPath)
    }

    override fun initializeViewBinding(v: View) = ItemWithTextBinding.bind(v)
}
