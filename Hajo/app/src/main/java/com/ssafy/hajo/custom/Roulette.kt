package com.ssafy.hajo.custom

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import com.ssafy.hajo.util.RotateListener
import java.lang.Math.cos
import java.lang.Math.sin

class Roulette @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
    ) : View(context, attrs, defStyleAttr){
    private var percent = 70

    private val strokePaint = Paint() // 룰렛 테두리
    private val fillPaint = Paint() // 룰렛 내부 색
    private val textPaint = Paint() // 룰렛 칸 내 텍스트

    private var rouletteDataList = listOf("나","상대")

    init {
        strokePaint.apply {
            color = Color.BLACK
            style = Paint.Style.STROKE
            strokeWidth = 15f
            isAntiAlias = true
        }
        fillPaint.apply {
            style = Paint.Style.FILL
            isAntiAlias = true
        }
        textPaint.apply {
            color = Color.BLACK
            textSize = 35f
            textAlign = Paint.Align.CENTER
        }
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // 정사각형을 그리는 작업
        val recLeft = left.toFloat() + paddingLeft
        val rectRight = right - paddingRight.toFloat()
        val rectTop = height / 2f - rectRight / 2f
        val rectBottom = height / 2f + rectRight / 2f

        // 정사각형 내부에 맞는 원을 그린다.
        val rectF = RectF(recLeft, rectTop, rectRight, rectBottom)
        drawRoulette(canvas, rectF)

    }

    private fun drawRoulette(canvas: Canvas, rectF: RectF) {
        // 테두리 그리는 작업
        canvas.drawArc(rectF, 0f, 360f, true, strokePaint)

        val sweepAngle = 360f * (percent / 100f) // 룰렛 한 칸의 각도
        val colors = listOf("#b8f3b8", "#ffcccc")

        // 룰렛의 중앙 계산
        val centerX = (rectF.left + rectF.right) / 2
        val centerY = (rectF.top + rectF.bottom) / 2

        // 텍스트 배치를 위해 사용할 반지름
        val radius = (rectF.right - rectF.left) / 2 * 0.5

        // 나의 룰렛 색상과 텍스트 배치 작업
        fillPaint.color = Color.parseColor(colors[0])

        var startAngle = 0f
        canvas.drawArc(rectF, startAngle, sweepAngle, true, fillPaint)

        val medianAngle = (startAngle + sweepAngle / 2f) * Math.PI / 180f
        var x = (centerX + (radius * cos(medianAngle))).toFloat()
        var y = (centerY + (radius * sin(medianAngle))).toFloat()

        // 나의 text rotate 각도 계산
        var d = (90f - (sweepAngle/2f) - (sweepAngle * 0)) + 180f

        canvas.rotate(-d, x,y)
        canvas.drawText(rouletteDataList[0], x, y, textPaint)
        canvas.rotate(d, x,y)

        // 상대방의 룰렛 색상과 텍스트 배치 작업
        fillPaint.color = Color.parseColor(colors[1])

        startAngle = sweepAngle
        canvas.drawArc(rectF, startAngle, 360 - startAngle, true, fillPaint)

        val xx = x - centerX
        val yy = centerY - y

        x = (centerX - xx)
        y = (centerY + yy)

        // text의 rotate 각도 계산
        d += 180f

        canvas.rotate(-d, x,y)
        canvas.drawText(rouletteDataList[1], x, y, textPaint)
        canvas.rotate(d, x,y)
    }

    // 룰렛 돌리는 함수
    fun rotateRoulette(degree: Float, duration: Long, rotateListener: RotateListener) {
        val sweepAngle = 360f * (percent / 100f)

        val animListener = object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {
                rotateListener.onRotateStart()
            }

            override fun onAnimationEnd(p0: Animation?) {
                val moveDegree = degree % 360
                var cDegree = if(moveDegree > 270) 360 - moveDegree + 270 else 270 - moveDegree
                val resultIndex = if(cDegree < sweepAngle) 0 else 1
                rotateListener.onRotateEnd(rouletteDataList[resultIndex])
            }

            override fun onAnimationRepeat(p0: Animation?) { }

        }

        val rotateAnim = RotateAnimation(
            0f, degree,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        rotateAnim.duration = duration
        rotateAnim.fillAfter = true
        rotateAnim.setAnimationListener(animListener)

        startAnimation(rotateAnim)
    }

    fun setPercent(p: Int) {
        percent = p
    }
}