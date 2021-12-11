package com.andreyyurko.firstapp.ui.emailconfirmation

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import com.andreyyurko.firstapp.R
import com.andreyyurko.firstapp.databinding.ViewVerificationCodeEditTextBinding
import com.andreyyurko.firstapp.databinding.ViewVerificationCodeSlotBinding
import kotlin.properties.Delegates
import android.view.View
import android.widget.LinearLayout









class VerificationCodeEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {

private val viewBinding =
    ViewVerificationCodeEditTextBinding.inflate(LayoutInflater.from(context), this)

var numberOfSlots: Int by Delegates.observable(0) { _, _, newValue ->
    val linearLayout = viewBinding.slotContainer
    for (i in 0 until newValue) {
        LayoutInflater.from(context).inflate(
            R.layout.view_verification_code_linear_layout_element, linearLayout
        )
        // TODO: настраивать размер в зависимости от числа ячеек
        /*val view = VerificationCodeSlotView(context)
        val width = resources.getDimension(R.dimen.verification_code_slot_width).toInt()
        val height = resources.getDimension(R.dimen.verification_code_slot_height).toInt()
        val margStart = resources.getDimension(R.dimen.edit_text_half_horizontal_padding).toInt()
        val margEnd = resources.getDimension(R.dimen.edit_text_half_horizontal_padding).toInt()
        view.background = ContextCompat.getDrawable(context, R.drawable.selector_bg_verification_code_slot)
        view.layoutParams = LayoutParams(width, height)
        view.updateLayoutParams<LayoutParams> {
            marginStart = margStart
            marginEnd = margEnd
        }
        linearLayout.addView(view)*/
    }

    slotViews = mutableListOf<VerificationCodeSlotView>().apply {
        for (i in 0 until newValue) {
            this.add(linearLayout.getChildAt(i) as VerificationCodeSlotView)
        }
    }

    slotValues = Array(newValue) { null }

}

private var slotViews: List<VerificationCodeSlotView> = emptyList()

private lateinit var slotValues: Array<CharSequence?>

var onVerificationCodeFilledListener: (String) -> Unit = {}

var onVerificationCodeFilledChangeListener: (Boolean) -> Unit = {}

init {
    context
        .theme
        .obtainStyledAttributes(
            attrs,
            R.styleable.VerificationCodeEditText,
            defStyleAttr,
            defStyleRes
        )
        .apply {
            numberOfSlots = getInt(R.styleable.VerificationCodeEditText_vcet_numberOfSlots, 4)
        }
    viewBinding.realVerificationCodeEditText.addTextChangedListener(

        object : TextWatcher {

            private var wasClearedLastSlot = false

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                wasClearedLastSlot = !wasClearedLastSlot && start + before == slotViews.size && count == 0
            }

            override fun afterTextChanged(s: Editable) {
                slotValues.fillWith(s)
                slotViews.render(slotValues)
                slotViews.moveCursorToFirstEmptySlot(slotValues)
                val filled = slotValues.isFilled()
                onVerificationCodeFilledChangeListener(filled)
                if (filled) onVerificationCodeFilledListener(slotValues.toCodeString())
                // Uncomment if we need to clear the whole field on backspace.
                // if (wasClearedLastSlot) viewBinding.realVerificationCodeEditText.setText("")
            }
        }
    )
    viewBinding.realVerificationCodeEditText.setOnFocusChangeListener { _, focused ->
        if (focused) {
            slotViews.moveCursorToFirstEmptySlot(slotValues)
        }
    }
    slotViews.forEach {
        (it.viewBinding.cursorView.background as AnimationDrawable).start()
    }
    slotValues.fillWith(viewBinding.realVerificationCodeEditText.text)
    slotViews.render(slotValues)
}

fun getCode(): String {
    return slotValues.toCodeString()
}

fun clear() {
    viewBinding.realVerificationCodeEditText.setText("")
}

fun isFilled(): Boolean =
    slotValues.isFilled()

private fun List<VerificationCodeSlotView>.render(values: Array<CharSequence?>) {
    values.forEachIndexed { index, value ->
        val slotView = this[index]
        slotView.viewBinding.slotValueTextView.text = value
        slotView.viewBinding.root.isActivated = (value != null)
    }
}

private fun List<VerificationCodeSlotView>.moveCursorToFirstEmptySlot(values: Array<CharSequence?>) {
    val indexOfFirstEmptySlot = values.indexOfFirst { it == null }
    forEachIndexed { index, slotView ->
        slotView.viewBinding.cursorView.isVisible = (index == indexOfFirstEmptySlot)
    }
}

private fun Array<CharSequence?>.fillWith(s: Editable?): Array<CharSequence?> {
    if (s.isNullOrEmpty()) {
        fill(null)
        return this
    }
    val lowestSize = kotlin.math.min(size, s.length)
    var i = 0
    while (i < lowestSize) {
        this[i] = s.subSequence(i, i + 1)
        ++i
    }
    if (i < size) {
        fill(null, i, size)
    }
    return this
}


private fun Array<CharSequence?>.isFilled(): Boolean =
    all { it != null }

private fun Array<CharSequence?>.toCodeString(): String =
    joinToString(separator = "", prefix = "", postfix = "", limit = -1, truncated = "") { it ?: "" }
}

