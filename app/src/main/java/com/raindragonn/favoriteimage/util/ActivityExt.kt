package com.raindragonn.favoriteimage.util

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

fun FragmentActivity.getNavControllerById(@IdRes id: Int): NavController {
    return (supportFragmentManager.findFragmentById(id) as NavHostFragment).navController
}