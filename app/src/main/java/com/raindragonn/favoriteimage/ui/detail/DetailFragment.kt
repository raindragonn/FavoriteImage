package com.raindragonn.favoriteimage.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.raindragonn.favoriteimage.R
import com.raindragonn.favoriteimage.databinding.FragmentDetailBinding
import com.raindragonn.favoriteimage.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {
    private val _binding: FragmentDetailBinding by viewBinding(FragmentDetailBinding::bind)
    private val _vm: DetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}