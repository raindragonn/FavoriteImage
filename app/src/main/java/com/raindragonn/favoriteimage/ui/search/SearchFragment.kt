package com.raindragonn.favoriteimage.ui.search

import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.raindragonn.favoriteimage.R
import com.raindragonn.favoriteimage.databinding.FragmentSearchBinding
import com.raindragonn.favoriteimage.domain.entity.Image
import com.raindragonn.favoriteimage.ui.adapter.ImageAdapter
import com.raindragonn.favoriteimage.util.ext.hideKeyboard
import com.raindragonn.favoriteimage.util.ext.viewRepeatOnLifeCycle
import com.raindragonn.favoriteimage.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.stateIn

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search), MenuProvider {
    private val _binding: FragmentSearchBinding by viewBinding(FragmentSearchBinding::bind)
    private val _vm: SearchViewModel by viewModels()
    private val _adapter: ImageAdapter by lazy { ImageAdapter(::onItemClick) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().addMenuProvider(this)

        initViews()
        observing()
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
                _vm.search(v.text.toString())
                v.hideKeyboard()
            }
            return@setOnEditorActionListener result
        }
        rvSearch.adapter = _adapter
    }

    private fun observing() = with(_vm) {
        viewRepeatOnLifeCycle(Lifecycle.State.STARTED) {
            imageListState
                .stateIn(this)
                .collect { result ->
                    result
                        .onSuccess {
                            _adapter.submitList(it)
                        }
                }
        }
    }

    private fun onItemClick(image: Image) {
        val action = SearchFragmentDirections.actionSearchFragmentToDetailFragment(image)

        findNavController()
            .navigate(action)
    }
}