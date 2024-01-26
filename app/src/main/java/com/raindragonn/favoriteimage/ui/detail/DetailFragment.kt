package com.raindragonn.favoriteimage.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.raindragonn.favoriteimage.R
import com.raindragonn.favoriteimage.databinding.FragmentDetailBinding
import com.raindragonn.favoriteimage.domain.entity.Image
import com.raindragonn.favoriteimage.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {
    private val _binding: FragmentDetailBinding by viewBinding(FragmentDetailBinding::bind)
    private val _vm: DetailViewModel by viewModels()
    private val _nav: DetailFragmentArgs by navArgs<DetailFragmentArgs>()

    private val _image: Image
        get() = _nav.image

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() = with(_binding) {
        val requestManager = Glide.with(requireContext())
        val requestOptions =
            RequestOptions()
                .transform(CenterCrop(), RoundedCorners(24))

        val thumbnailBuilder =
            requestManager
                .load(_image.thumbnailUrl)
                .apply(requestOptions)

        requestManager
            .load(_image.originUrl)
            .thumbnail(thumbnailBuilder)
            .apply(requestOptions)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(ivImage)

        tvId.text = _image.id
        tvAuthor.text = _image.author
        tvSize.text = _image.size
        tvCreatedAt.text = _image.formattedTime
    }
}