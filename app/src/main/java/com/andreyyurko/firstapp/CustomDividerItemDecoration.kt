package com.andreyyurko.firstapp

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView

class CustomPositionItemDecoration(private val dividerDrawable: Drawable) :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(rect: Rect, view: View, parent: RecyclerView, s: RecyclerView.State) {
        rect.bottom = dividerDrawable.intrinsicHeight
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        parent.children
            .forEach { view ->
                val top = view.bottom
                val left = view.paddingLeft
                val right = parent.right - view.paddingRight
                val bottom = top + dividerDrawable.intrinsicHeight
                dividerDrawable.bounds = Rect(left, top, right, bottom)
                dividerDrawable.draw(canvas)

            }
    }

}