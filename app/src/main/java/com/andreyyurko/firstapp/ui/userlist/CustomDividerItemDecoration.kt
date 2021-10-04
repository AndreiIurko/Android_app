package com.andreyyurko.firstapp.ui.userlist

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView

class CustomDividerItemDecoration(private val dividerDrawable: Drawable) :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(rect: Rect, view: View, parent: RecyclerView, s: RecyclerView.State) {
        rect.bottom = dividerDrawable.intrinsicHeight
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        parent.adapter?.let { adapter ->
            parent.children
                .forEach { view ->
                    val childAdapterPosition = parent.getChildAdapterPosition(view)
                        .let { if (it == RecyclerView.NO_POSITION) return else it }
                    if (childAdapterPosition != adapter.itemCount - 1) {
                        val top = view.bottom
                        val left = view.paddingLeft
                        val right = parent.right - view.paddingRight
                        val bottom = top + dividerDrawable.intrinsicHeight
                        dividerDrawable.bounds = Rect(left, top, right, bottom)
                        dividerDrawable.draw(canvas)
                    }

                }
        }
    }

}