package com.raindragonn.favoriteimage.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.raindragonn.favoriteimage.R
import com.raindragonn.favoriteimage.databinding.ActivityMainBinding
import com.raindragonn.favoriteimage.util.ext.getNavControllerById
import com.raindragonn.favoriteimage.util.ext.getNavHostFragment
import com.raindragonn.favoriteimage.util.view.FabVisibleListener
import com.raindragonn.favoriteimage.util.view.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), FabVisibleListener {
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

        fabScroll.setOnClickListener {
            getNavHostFragment(R.id.nav_host_fragment)
                .childFragmentManager
                .setFragmentResult(KEY_FAB_CLICK, bundleOf())
        }
    }

    override fun hide() {
        _binding.fabScroll.hide()
    }

    override fun show() {
        _binding.fabScroll.show()
    }

    companion object {
        const val KEY_FAB_CLICK = "key_fab_click"
    }
}