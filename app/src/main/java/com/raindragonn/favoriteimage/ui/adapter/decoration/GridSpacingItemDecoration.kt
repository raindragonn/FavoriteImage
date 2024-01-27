package com.raindragonn.favoriteimage.ui.adapter.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.raindragonn.favoriteimage.util.ext.dpToRoundedPx

/**
 * https://stackoverflow.com/a/30701422
 */

internal class GridSpacingItemDecoration(
    private val spanCount: Int = DEFAULT_SPAN_COUNT,
    private val spacing: Int = DEFAULT_SPACING,
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position: Int = parent.getChildAdapterPosition(view)
        if (position == RecyclerView.NO_POSITION) return

        val column: Int = position % spanCount

        outRect.apply {
            outRect.left = column * spacing / spanCount
            outRect.right = spacing - (column + 1) * spacing / spanCount
            if (position >= spanCount) {
                outRect.top = spacing
            }
        }
    }

    companion object {
        private const val DEFAULT_SPAN_COUNT = 4
        private val DEFAULT_SPACING = 2f.dpToRoundedPx()
    }
}   