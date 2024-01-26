package com.raindragonn.favoriteimage.ui.search.adapter

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.raindragonn.favoriteimage.databinding.ItemSearchBinding
import com.raindragonn.favoriteimage.domain.entity.Image

class SearchViewHolder(
    private val _binding: ItemSearchBinding
) : RecyclerView.ViewHolder(_binding.root) {

    fun bind(image: Image) = with(_binding) {
        val requestManager = Glide.with(ivImage)
        val thumbNailRequest = requestManager
            .load(image.thumbnailUrl)
            .centerCrop()

        requestManager
            .load(image.originUrl)
            .thumbnail(thumbNailRequest)
            .centerCrop()
            .into(ivImage)

        ivFavorite.isVisible = image.isLike
    }
}