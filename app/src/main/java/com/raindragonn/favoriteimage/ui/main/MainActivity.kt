package com.raindragonn.favoriteimage.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.raindragonn.favoriteimage.R
import com.raindragonn.favoriteimage.databinding.ActivityMainBinding
import com.raindragonn.favoriteimage.util.ext.getNavControllerById
import com.raindragonn.favoriteimage.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
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
    }
}