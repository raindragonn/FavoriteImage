package com.raindragonn.favoriteimage.ui.search

import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.raindragonn.favoriteimage.R
import com.raindragonn.favoriteimage.databinding.FragmentSearchBinding
import com.raindragonn.favoriteimage.domain.entity.Image
import com.raindragonn.favoriteimage.domain.pager.LoadState
import com.raindragonn.favoriteimage.domain.util.ListEmptyException
import com.raindragonn.favoriteimage.domain.util.OverPageException
import com.raindragonn.favoriteimage.ui.adapter.ImagePagerAdapter
import com.raindragonn.favoriteimage.ui.adapter.decoration.GridSpacingItemDecoration
import com.raindragonn.favoriteimage.ui.main.MainActivity
import com.raindragonn.favoriteimage.util.ext.hideKeyboard
import com.raindragonn.favoriteimage.util.ext.viewLifeCycleScope
import com.raindragonn.favoriteimage.util.ext.viewRepeatOnLifeCycle
import com.raindragonn.favoriteimage.util.view.FabVisibleListener
import com.raindragonn.favoriteimage.util.view.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.IOException

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search), MenuProvider {
    private val _binding: FragmentSearchBinding by viewBinding(FragmentSearchBinding::bind)
    private val _vm: SearchViewModel by viewModels()
    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val position =
                (_binding.rvSearch.layoutManager as? GridLayoutManager)?.findFirstCompletelyVisibleItemPosition()
                    ?: return
            if (position == 0) {
                _fabVisibleListener?.hide()
            } else {
                _fabVisibleListener?.show()
            }
        }
    }

    private val _searchText: String
        get() = _binding.etSearch.text.toString()

    private val _fabVisibleListener: FabVisibleListener?
        get() = requireActivity() as? FabVisibleListener

    private var _adapter: ImagePagerAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().addMenuProvider(this)

        initViews()
        observing()
        setupResultListener()
    }

    override fun onDestroyView() {
        requireActivity().removeMenuProvider(this)
        super.onDestroyView()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_search, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.menu_item_like) {
            openFavorite()
            return true
        }
        return false
    }

    private fun openFavorite() {
        val action: NavDirections = SearchFragmentDirections.actionSearchFragmentToLikeFragment()
        findNavController().navigate(action)
    }

    private fun initViews() = with(_binding) {
        etSearch.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) v.hideKeyboard()
        }

        etSearch.setOnEditorActionListener { v, actionId, event ->
            val result = (actionId == EditorInfo.IME_ACTION_SEARCH)
                    || (event.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER)
            if (result) {
                search()
                v.hideKeyboard()
            }
            return@setOnEditorActionListener result
        }
        rvSearch.addItemDecoration(GridSpacingItemDecoration())
        rvSearch.adapter = _adapter
        rvSearch.addOnScrollListener(scrollListener)
    }

    private fun observing() = with(_vm) {
        viewRepeatOnLifeCycle(Lifecycle.State.STARTED) {
            launch {
                imageListStateFlow
                    .stateIn(this)
                    .collect {
                        _adapter?.submitList(it)
                    }
            }
            launch {
                loadStateFlow
                    .stateIn(this)
                    .collect(::onChangeLoadState)
            }
        }
    }

    private fun search() = with(_vm) {
        val pager = getImagePager(_searchText)
        _binding.rvSearch.adapter = ImagePagerAdapter(pager, ::onItemClick).also {
            _adapter = it
        }

        viewLifeCycleScope.launch {
            setLoadStateFlow(pager.loadStateFlow)
            setSearchImageFlow(pager.flow)
        }
    }

    private fun onChangeLoadState(loadState: LoadState) {
        _binding.tvSearchGuide.isVisible = false
        _binding.pbLoading.isVisible = loadState is LoadState.Loading

        if (loadState is LoadState.Error) {
            val messageId = when (loadState.throwable) {
                is OverPageException -> return
                is ListEmptyException -> {
                    _binding.tvSearchGuide.isVisible = true
                    return
                }

                is IOException -> R.string.internet_error_message
                else -> R.string.default_error_message
            }
            Toast.makeText(requireContext(), messageId, Toast.LENGTH_SHORT).show()
        }
    }

    private fun onItemClick(image: Image) {
        val action = SearchFragmentDirections.actionSearchFragmentToDetailFragment(image)

        findNavController()
            .navigate(action)
    }

    private fun setupResultListener() {
        setFragmentResultListener(MainActivity.KEY_FAB_CLICK) { _, _ ->
            _binding.rvSearch.smoothScrollToPosition(0)
        }
    }
}