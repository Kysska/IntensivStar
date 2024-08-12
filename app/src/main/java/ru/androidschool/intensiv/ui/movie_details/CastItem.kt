package ru.androidschool.intensiv.ui.movie_details

import android.view.View
import com.squareup.picasso.Picasso
import com.xwray.groupie.viewbinding.BindableItem
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.databinding.ItemCastBinding
import ru.androidschool.intensiv.domain.entity.CastCard

class CastItem(
    private val content: CastCard
) : BindableItem<ItemCastBinding>() {

    override fun getLayout(): Int = R.layout.item_cast

    override fun bind(view: ItemCastBinding, position: Int) {
        view.nameTextView.text = content.name

        Picasso.get()
            .load(content.posterPath)
            .into(view.imageView)
    }

    override fun initializeViewBinding(v: View) = ItemCastBinding.bind(v)
}
