package com.ssafy.hajo.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecyclerItemDecoration(
    var divTop: Int,
    var divBottom: Int,
    var divLeft: Int,
    var divRight: Int
) : RecyclerView.ItemDecoration() {


    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.top = divTop
        outRect.bottom = divBottom
        outRect.left = divLeft
        outRect.right = divRight

    }
}