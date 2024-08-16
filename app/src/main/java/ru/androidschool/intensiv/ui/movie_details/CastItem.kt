package ru.androidschool.intensiv.ui.movie_details

import android.view.View
import com.xwray.groupie.viewbinding.BindableItem
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.databinding.ItemCastBinding
import ru.androidschool.intensiv.domain.entity.CastCard
import ru.androidschool.intensiv.utils.extensions.loadImage

class CastItem(
    private val content: CastCard
) : BindableItem<ItemCastBinding>() {

    override fun getLayout(): Int = R.layout.item_cast

    override fun bind(view: ItemCastBinding, position: Int) {
        view.nameTextView.text = content.name

        view.imageView.loadImage(content.posterPath)
    }

    override fun initializeViewBinding(v: View) = ItemCastBinding.bind(v)
}
