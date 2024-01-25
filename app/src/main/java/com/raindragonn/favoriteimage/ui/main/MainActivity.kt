package com.raindragonn.favoriteimage.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDirections
import com.raindragonn.favoriteimage.R
import com.raindragonn.favoriteimage.databinding.ActivityMainBinding
import com.raindragonn.favoriteimage.ui.search.SearchFragmentDirections
import com.raindragonn.favoriteimage.util.getNavControllerById
import com.raindragonn.favoriteimage.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {
    private val _binding: ActivityMainBinding by viewBinding(ActivityMainBinding::inflate)
    private val _navController: NavController by lazy { getNavControllerById(R.id.nav_host_fragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(_binding.root)

        _navController.addOnDestinationChangedListener(this)
        initViews()
    }

    override fun onDestroy() {
        _navController.removeOnDestinationChangedListener(this)
        super.onDestroy()
    }

    private fun initViews() = with(_binding) {
        btnFavorite.setOnClickListener { openFavorite() }
    }

    private fun openFavorite() {
        val action: NavDirections = SearchFragmentDirections.actionSearchFragmentToLikeFragment()
        _navController.navigate(action)
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        _binding.apply {
            tvTitle.text = destination.label
            btnFavorite.isVisible = destination.id == R.id.searchFragment
        }
    }
}