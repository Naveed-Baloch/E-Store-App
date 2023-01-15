package com.example.estore.ui.common

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*

class SpacingItemDecoration(
    val left: Int = 0,
    val top: Int = 0,
    val right: Int = 0,
    val bottom: Int = 0,
    val firstLeft: Int = 0,
    val lastRight: Int = 0) : ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: State) {

        outRect.top = top
        outRect.bottom = bottom

        val position = parent.getChildLayoutPosition(view)
        val itemCount = parent.adapter?.itemCount ?: 0

        if (position == 0 && firstLeft > left) {
            outRect.left = firstLeft
        } else {
            outRect.left = left
        }

        if (position == itemCount -1 && lastRight > right) {
            outRect.right = lastRight
        } else {
            outRect.right = right
        }
    }
}