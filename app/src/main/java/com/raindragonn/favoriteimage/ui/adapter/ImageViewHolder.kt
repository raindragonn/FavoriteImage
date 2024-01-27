package com.raindragonn.favoriteimage.ui.adapter

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.raindragonn.favoriteimage.databinding.ItemImageBinding
import com.raindragonn.favoriteimage.domain.entity.Image

class ImageViewHolder(
    private val _binding: ItemImageBinding
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
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(ivImage)

        ivFavorite.isVisible = image.liked
    }
}