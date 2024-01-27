package com.raindragonn.favoriteimage.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import com.raindragonn.favoriteimage.databinding.ItemImageBinding
import com.raindragonn.favoriteimage.domain.entity.Image
import com.raindragonn.favoriteimage.domain.pager.PagingBindListener
import com.raindragonn.favoriteimage.pager.PagerListAdapter
import com.raindragonn.favoriteimage.util.ext.checkNoPosition

class ImagePagerAdapter(
    pagingBindListener: PagingBindListener,
    private val _onClickImage: (image: Image) -> Unit,
) : PagerListAdapter<Image, ImageViewHolder>(pagingBindListener, _differ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding: ItemImageBinding =
            ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
            .apply {
                binding.root.setOnClickListener {
                    checkNoPosition { position ->
                        _onClickImage(getItem(position))
                    }
                }
            }
    }

    override fun onBindViewHolder(holder: ImageViewHolder, item: Image) {
        holder.bind(item)
    }

    companion object {
        private val _differ = object : ItemCallback<Image>() {
            override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
                return oldItem.thumbnailUrl == newItem.thumbnailUrl
                        && oldItem.originUrl == newItem.originUrl
                        && oldItem.author == newItem.author
                        && oldItem.width == newItem.width
                        && oldItem.height == newItem.height
                        && oldItem.liked == newItem.liked
            }
        }

    }
}