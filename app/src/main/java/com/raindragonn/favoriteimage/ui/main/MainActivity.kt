package com.raindragonn.favoriteimage.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.isInvisible
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.appbar.AppBarLayout
import com.raindragonn.favoriteimage.R
import com.raindragonn.favoriteimage.databinding.ActivityMainBinding
import com.raindragonn.favoriteimage.util.ext.getNavControllerById
import com.raindragonn.favoriteimage.util.ext.getNavHostFragment
import com.raindragonn.favoriteimage.util.view.FabVisibleChangeListener
import com.raindragonn.favoriteimage.util.view.viewBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener,
    FabVisibleChangeListener {
    private val _binding: ActivityMainBinding by viewBinding(ActivityMainBinding::inflate)
    private val _navController: NavController by lazy { getNavControllerById(R.id.nav_host_fragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(_binding.root)

        initViews()
    }

    override fun onSupportNavigateUp(): Boolean {
        return _navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun initViews() = with(_binding) {
        setSupportActionBar(toolbar)
        setupActionBarWithNavController(_navController)

        _navController
            .addOnDestinationChangedListener(this@MainActivity)

        fabScroll.setOnClickListener {
            getNavHostFragment(R.id.nav_host_fragment)
                .childFragmentManager
                .setFragmentResult(KEY_FAB_CLICK, bundleOf())
        }
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        if (destination.id == R.id.detailFragment) {
            disableScrolling()
        } else {
            enableScrolling()
        }
    }

    override fun hideFab() {
        _binding.fabScroll.hide()
    }

    override fun showFab() {
        _binding.fabScroll.show()
    }

    override fun invisibleFab() {
        _binding.fabScroll.isInvisible = true
    }

    private fun enableScrolling() {
        val layoutParams: AppBarLayout.LayoutParams =
            _binding.collapsingToolbarLayout.layoutParams as AppBarLayout.LayoutParams
        layoutParams.scrollFlags =
            AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
    }

    private fun disableScrolling() {
        val layoutParams =
            _binding.collapsingToolbarLayout.layoutParams as AppBarLayout.LayoutParams
        layoutParams.scrollFlags = 0
    }

    companion object {
        const val KEY_FAB_CLICK = "key_fab_click"
    }
}