package com.raindragonn.favoriteimage.ui.like

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.raindragonn.favoriteimage.R
import com.raindragonn.favoriteimage.databinding.FragmentLikeBinding
import com.raindragonn.favoriteimage.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LikeFragment : Fragment(R.layout.fragment_like) {
    private val _binding: FragmentLikeBinding by viewBinding(FragmentLikeBinding::bind)
    private val _vm: LikeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}