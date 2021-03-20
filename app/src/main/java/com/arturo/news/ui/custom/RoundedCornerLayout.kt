package com.arturo.news.ui.custom

import android.content.Context
import android.graphics.*
import android.util.AttributeSet

import androidx.constraintlayout.widget.ConstraintLayout

import com.arturo.news.R

class RoundedCornerLayout @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(
    context!!, attrs, defStyle
) {
    companion object {
        private const val DEFAULT_CORNER_RADIUS_PX = 0f
    }

    private var maskBitmap: Bitmap? = null
    private var paint: Paint? = null
    private var maskPaint: Paint? = null
    private var cornerRadiusPx = DEFAULT_CORNER_RADIUS_PX

    init {
        if (context != null && attrs != null) {
            val attributes = context.obtainStyledAttributes(attrs, R.styleable.RoundedCornerLayout)
            cornerRadiusPx = attributes.getDimension(
                R.styleable.RoundedCornerLayout_cornerRadius,
                DEFAULT_CORNER_RADIUS_PX
            )
            attributes.recycle()
        }
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        maskPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
        maskPaint!!.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        setWillNotDraw(false)
    }

    override fun draw(canvas: Canvas) {
        val offscreenBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val offscreenCanvas = Canvas(offscreenBitmap)
        super.draw(offscreenCanvas)
        if (maskBitmap == null) {
            maskBitmap = createMask(width, height)
        }
        offscreenCanvas.drawBitmap(maskBitmap!!, 0f, 0f, maskPaint)
        canvas.drawBitmap(offscreenBitmap, 0f, 0f, paint)
    }

    private fun createMask(width: Int, height: Int): Bitmap {
        val mask = Bitmap.createBitmap(width, height, Bitmap.Config.ALPHA_8)
        val canvas = Canvas(mask)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = Color.WHITE
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        canvas.drawRoundRect(
            RectF(0f, 0f, width.toFloat(), height.toFloat()),
            cornerRadiusPx,
            cornerRadiusPx,
            paint
        )
        return mask
    }
}