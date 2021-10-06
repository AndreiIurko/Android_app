package com.andreyyurko.firstapp.ui.userlist

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView

class CustomDividerItemDecorationOnboarding(private val dividerDrawable: Drawable) :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(rect: Rect, view: View, parent: RecyclerView, s: RecyclerView.State) {
        rect.left = dividerDrawable.intrinsicWidth
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        parent.adapter?.let { adapter ->
            parent.children
                .forEach { view ->
                    val childAdapterPosition = parent.getChildAdapterPosition(view)
                        .let { if (it == RecyclerView.NO_POSITION) return else it }
                    if (childAdapterPosition != adapter.itemCount - 1) {
                        val top = view.top + view.paddingTop
                        val left = view.right
                        val right = left + dividerDrawable.intrinsicWidth
                        val bottom = view.bottom - view.paddingBottom
                        dividerDrawable.bounds = Rect(left, top, right, bottom)
                        dividerDrawable.draw(canvas)
                    }

                }
        }
    }

}