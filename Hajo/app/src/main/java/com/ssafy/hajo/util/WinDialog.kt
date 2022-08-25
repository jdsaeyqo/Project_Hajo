package com.ssafy.hajo.util

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import com.ssafy.hajo.R
import com.ssafy.hajo.databinding.DialogWinBinding
import com.ssafy.hajo.entity.Match
import java.lang.Math.sqrt

class WinDialog(context: Context, match: Match, private val rollCallback: (Int) -> Unit): Dialog(context), SensorEventListener {
    lateinit var binding: DialogWinBinding
    private var shaked = false

    private val rotateListener = object : RotateListener {
        override fun onRotateStart() {
        }

        override fun onRotateEnd(result: String) {
            Log.d("tttt", "onRotateEnd: $result")
            val points = (1..99).random()
            when(result) {
                "나" -> {
                    binding.tvShake.setText("+${points}p 당첨!!!")
                    rollCallback(points + 100)
                }
                else -> {
                    binding.tvShake.setText("꽝..")
                }
            }
        }

    }

    private val sensorManager by lazy {
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogWinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        // 뒷 배경 어둡게 처리
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // 룰렛 화살표 최상단으로 표시
        binding.ivArrow.bringToFront()

        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL)

        // Y축을 기준으로 회전하는 animation
        val oa = ObjectAnimator.ofFloat(binding.cvResult, "rotationY", 0f, 360f).apply {
            setDuration(800)
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    binding.clRoulette.visibility = View.VISIBLE
                }

                override fun onAnimationStart(animation: Animator?) {
                    super.onAnimationStart(animation)
                    binding.tvResult.visibility = View.GONE
                    binding.cvRoulette.visibility = View.GONE
                    binding.tvRoll.visibility = View.GONE
                }
            })
        }

        // 승리 효과 애니메이션 적용
        setAnimation()

        // 룰렛 화면으로 이동
        binding.cvRoulette.setOnClickListener {
            // 승리 효과 애니메이션 종료
            finishAnimation()

            oa.start()
            val percent = 100
            binding.roulette.setPercent(percent)
        }
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        val x = p0!!.values[0]
        val y = p0.values[1]
        val z = p0.values[2]

        if(sqrt(((x*x+y*y+z*z).toDouble())) > 20) {
            if(!shaked) {
                shaked = true
                val degree = (2000 .. 10000).random().toFloat()
                binding.roulette.rotateRoulette(degree, 3000, rotateListener)
            }
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) { }
    
    fun setAnimation() {
        var animation = AnimationUtils.loadAnimation(context, R.anim.win_anim)
        binding.ivFirework1.startAnimation(animation)
        animation = AnimationUtils.loadAnimation(context, R.anim.win_anim2)
        binding.ivFirework2.startAnimation(animation)
        animation = AnimationUtils.loadAnimation(context, R.anim.win_anim3)
        binding.ivFirework3.startAnimation(animation)
        animation = AnimationUtils.loadAnimation(context, R.anim.win_anim4)
        binding.ivFirework4.startAnimation(animation)
    }

    fun finishAnimation() {
        binding.apply {
            ivFirework1.clearAnimation()
            ivFirework1.visibility = View.GONE
            ivFirework2.clearAnimation()
            ivFirework2.visibility = View.GONE
            ivFirework3.clearAnimation()
            ivFirework3.visibility = View.GONE
            ivFirework4.clearAnimation()
            ivFirework4.visibility = View.GONE
        }
    }
}