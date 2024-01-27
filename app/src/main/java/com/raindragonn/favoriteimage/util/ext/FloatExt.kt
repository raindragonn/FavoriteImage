package com.raindragonn.favoriteimage.util.ext

import android.content.res.Resources

fun Float.dpToRoundedPx(): Int =
    (this * Resources.getSystem().displayMetrics.density).toInt()