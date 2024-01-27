package com.raindragonn.favoriteimage.ui.like

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.raindragonn.favoriteimage.R
import com.raindragonn.favoriteimage.databinding.FragmentLikeBinding
import com.raindragonn.favoriteimage.domain.entity.Image
import com.raindragonn.favoriteimage.ui.adapter.LikedImageAdapter
import com.raindragonn.favoriteimage.ui.adapter.decoration.GridSpacingItemDecoration
import com.raindragonn.favoriteimage.ui.main.MainActivity
import com.raindragonn.favoriteimage.util.ext.viewRepeatOnLifeCycle
import com.raindragonn.favoriteimage.util.view.FabVisibleListener
import com.raindragonn.favoriteimage.util.view.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.stateIn

@AndroidEntryPoint
class LikeFragment : Fragment(R.layout.fragment_like) {
    private val _binding: FragmentLikeBinding by viewBinding(FragmentLikeBinding::bind)
    private val _vm: LikeViewModel by viewModels()
    private val _adapter: LikedImageAdapter by lazy { LikedImageAdapter(::onItemClick) }
    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val position =
                (_binding.rvLike.layoutManager as? GridLayoutManager)?.findFirstCompletelyVisibleItemPosition()
                    ?: return
            if (position == 0) {
                _fabVisibleListener?.hide()
            } else {
                _fabVisibleListener?.show()
            }
        }
    }

    private val _fabVisibleListener: FabVisibleListener?
        get() = requireActivity() as? FabVisibleListener

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        observing()
        setupResultListener()
    }

    private fun initViews() = with(_binding) {
        rvLike.addItemDecoration(GridSpacingItemDecoration())
        rvLike.adapter = _adapter
        rvLike.addOnScrollListener(scrollListener)
    }

    private fun observing() = with(_vm) {
        viewRepeatOnLifeCycle(Lifecycle.State.STARTED) {
            getLikedImageState()
                .stateIn(this)
                .collect(_adapter::submitList)
        }
    }

    private fun setupResultListener() {
        setFragmentResultListener(MainActivity.KEY_FAB_CLICK) { _, _ ->
            _binding.rvLike.smoothScrollToPosition(0)
        }
    }

    private fun onItemClick(image: Image) {
        val action = LikeFragmentDirections.actionLikeFragmentToDetailFragment(image)
        findNavController()
            .navigate(action)
    }
}