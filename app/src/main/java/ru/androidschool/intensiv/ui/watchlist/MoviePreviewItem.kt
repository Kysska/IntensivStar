package ru.androidschool.intensiv.ui.watchlist

import android.view.View
import com.xwray.groupie.viewbinding.BindableItem
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.databinding.ItemSmallBinding
import ru.androidschool.intensiv.domain.entity.MovieCard
import ru.androidschool.intensiv.utils.extensions.loadImage

class MoviePreviewItem(
    private val content: MovieCard,
    private val onClick: (movie: MovieCard) -> Unit
) : BindableItem<ItemSmallBinding>() {

    override fun getLayout() = R.layout.item_small

    override fun bind(view: ItemSmallBinding, position: Int) {
        view.imagePreview.setOnClickListener {
            onClick.invoke(content)
        }

        view.imagePreview.loadImage(content.posterPath)
    }

    override fun initializeViewBinding(v: View): ItemSmallBinding = ItemSmallBinding.bind(v)
}
