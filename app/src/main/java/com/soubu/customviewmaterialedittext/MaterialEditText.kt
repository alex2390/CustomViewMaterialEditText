package com.soubu.customviewmaterialedittext

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatEditText

/**
 * Created by renjiawen on 2021/1/25
 *
 * Desc: ma
 **/

private var TEXT_SIZE = 12.dp
private var TEXT_MARGIN = 8.dp
private val VERTICAL_OFFSET = 23.dp
private val EXTRA_VERTICAL_OFFSET = 16.dp
private val HORIZONTAL_OFFSET = 5.dp

class MaterialEditText(context: Context, attrs: AttributeSet?) : AppCompatEditText(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var floatingLabelShown = false
    private var floatingLabelFraction = 0f
        set(value) {
            field = value
            invalidate()
        }

    private val animator by lazy {
        ObjectAnimator.ofFloat(this, "floatingLabelFraction", 0f, 1f)
    }

    var useFloatingLabel = false
        set(value) {
            if (field != value) {
                field = value
                if (field) {
                    setPadding(
                        paddingLeft,
                        paddingTop + (TEXT_MARGIN + TEXT_SIZE).toInt(),
                        paddingRight,
                        paddingBottom
                    )
                } else {
                    setPadding(
                        paddingLeft,
                        paddingTop - (TEXT_MARGIN + TEXT_SIZE).toInt(),
                        paddingRight,
                        paddingBottom
                    )
                }
            }
        }

    init {
        paint.textSize = TEXT_SIZE
        var typedArray = context.obtainStyledAttributes(attrs, intArrayOf(R.attr.useFloatingLabel))
        useFloatingLabel = typedArray.getBoolean(0, true)
        typedArray.recycle()
    }

    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        if (text.isNullOrEmpty() && floatingLabelShown) {
            floatingLabelShown = false
            animator.reverse()

        } else if (!text.isNullOrEmpty() && !floatingLabelShown) {
            floatingLabelShown = true
            animator.start()

        }

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // 0xff 透明度
        paint.alpha = (0xff * floatingLabelFraction).toInt()
        var currentVerticaloffset =
            VERTICAL_OFFSET + EXTRA_VERTICAL_OFFSET * (1 - floatingLabelFraction)
        canvas.drawText(hint.toString(), HORIZONTAL_OFFSET, currentVerticaloffset, paint)


    }


}