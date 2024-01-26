package com.raindragonn.favoriteimage.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import com.raindragonn.favoriteimage.databinding.ItemSearchBinding
import com.raindragonn.favoriteimage.domain.entity.Image
import com.raindragonn.favoriteimage.util.checkNoPosition

class SearchAdapter(
    private val _onClickImage: (image: Image) -> Unit,
    private val _onClickFavorite: (id: String) -> Unit,
) : ListAdapter<Image, SearchViewHolder>(_differ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding: ItemSearchBinding =
            ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
            .apply {
                binding.root.setOnClickListener {
                    checkNoPosition { position ->
                        _onClickImage(getItem(position))
                    }
                }
                binding.ivFavorite.setOnClickListener {
                    checkNoPosition { position ->
                        _onClickFavorite(getItem(position).id)
                    }
                }
            }
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(getItem(position))
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
                        && oldItem.isLike == newItem.isLike
            }
        }

    }
}