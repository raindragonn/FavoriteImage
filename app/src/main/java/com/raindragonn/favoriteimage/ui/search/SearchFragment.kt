package com.raindragonn.favoriteimage.ui.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.raindragonn.favoriteimage.R
import com.raindragonn.favoriteimage.databinding.FragmentSearchBinding
import com.raindragonn.favoriteimage.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {
    private val _binding: FragmentSearchBinding by viewBinding(FragmentSearchBinding::bind)
    private val _vm: SearchViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}