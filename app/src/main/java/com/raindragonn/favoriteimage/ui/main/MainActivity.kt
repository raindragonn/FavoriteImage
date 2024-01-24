package com.raindragonn.favoriteimage.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.raindragonn.favoriteimage.databinding.ActivityMainBinding
import com.raindragonn.favoriteimage.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val _binding: ActivityMainBinding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(_binding.root)
    }
}