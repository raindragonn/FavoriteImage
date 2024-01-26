package com.raindragonn.favoriteimage.ui.search

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.raindragonn.favoriteimage.R
import com.raindragonn.favoriteimage.databinding.FragmentSearchBinding
import com.raindragonn.favoriteimage.domain.entity.Image
import com.raindragonn.favoriteimage.ui.search.adapter.SearchAdapter
import com.raindragonn.favoriteimage.util.hideKeyboard
import com.raindragonn.favoriteimage.util.viewBinding
import com.raindragonn.favoriteimage.util.viewRepeatOnLifeCycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.stateIn

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {
    private val _binding: FragmentSearchBinding by viewBinding(FragmentSearchBinding::bind)
    private val _vm: SearchViewModel by viewModels()
    private val _adapter: SearchAdapter by lazy {
        SearchAdapter(
            ::onItemClick,
            ::onItemFavoriteClick
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        observing()
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

        rvSearch.layoutManager = GridLayoutManager(requireContext(), 3)
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

    private fun onItemFavoriteClick(id: String) {

    }
}