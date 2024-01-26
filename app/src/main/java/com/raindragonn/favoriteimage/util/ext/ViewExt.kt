package com.raindragonn.favoriteimage.util.ext

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(applicationWindowToken, 0)
}

fun ViewHolder.checkNoPosition(action: (position: Int) -> Unit) {
    if (adapterPosition != RecyclerView.NO_POSITION) {
        action(adapterPosition)
    }
}