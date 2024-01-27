package com.raindragonn.favoriteimage.ui.like

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.raindragonn.favoriteimage.R
import com.raindragonn.favoriteimage.databinding.FragmentLikeBinding
import com.raindragonn.favoriteimage.domain.entity.Image
import com.raindragonn.favoriteimage.ui.adapter.LikedImageAdapter
import com.raindragonn.favoriteimage.util.ext.viewRepeatOnLifeCycle
import com.raindragonn.favoriteimage.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.stateIn

@AndroidEntryPoint
class LikeFragment : Fragment(R.layout.fragment_like) {
    private val _binding: FragmentLikeBinding by viewBinding(FragmentLikeBinding::bind)
    private val _vm: LikeViewModel by viewModels()

    private val _adapter: LikedImageAdapter by lazy { LikedImageAdapter(::onItemClick) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        observing()
    }

    private fun initViews() = with(_binding) {
        rvLike.adapter = _adapter
    }

    private fun observing() = with(_vm) {
        viewRepeatOnLifeCycle(Lifecycle.State.STARTED) {
            getLikedImageState()
                .stateIn(this)
                .collect(_adapter::submitList)
        }
    }

    private fun onItemClick(image: Image) {
        val action = LikeFragmentDirections.actionLikeFragmentToDetailFragment(image)
        findNavController()
            .navigate(action)
    }
}