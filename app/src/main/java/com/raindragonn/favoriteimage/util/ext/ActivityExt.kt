package com.raindragonn.favoriteimage.util.ext

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

fun FragmentActivity.getNavControllerById(@IdRes id: Int): NavController {
    return (supportFragmentManager.findFragmentById(id) as NavHostFragment).navController
}

fun FragmentActivity.getNavHostFragment(@IdRes id: Int): NavHostFragment {
    return supportFragmentManager.findFragmentById(id) as NavHostFragment
}