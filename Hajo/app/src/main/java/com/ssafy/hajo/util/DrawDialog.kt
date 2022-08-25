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
import com.ssafy.hajo.databinding.DialogDrawBinding
import com.ssafy.hajo.databinding.DialogWinBinding
import com.ssafy.hajo.entity.Match
import java.lang.Math.sqrt

class DrawDialog(context: Context,val match: Match, private val callback: (Int) -> Unit): Dialog(context) {
    lateinit var binding: DialogDrawBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogDrawBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        // 뒷 배경 어둡게 처리
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // 효과 애니메이션 적용
        setAnimation()

        // 룰렛 화면으로 이동
        binding.cvClose.setOnClickListener {
            //  효과 애니메이션 종료
            finishAnimation()
            callback(-50)
            dismiss()
        }
    }


    fun setAnimation() {
        var animation = AnimationUtils.loadAnimation(context, R.anim.lose_anim)
        binding.ivCry1.startAnimation(animation)
        animation = AnimationUtils.loadAnimation(context, R.anim.lose_anim2)
        binding.ivCry2.startAnimation(animation)
        animation = AnimationUtils.loadAnimation(context, R.anim.lose_anim3)
        binding.ivCry3.startAnimation(animation)
        animation = AnimationUtils.loadAnimation(context, R.anim.lose_anim4)
        binding.ivCry4.startAnimation(animation)
    }

    fun finishAnimation() {
        binding.apply {
            ivCry1.clearAnimation()
            ivCry1.visibility = View.GONE
            ivCry2.clearAnimation()
            ivCry2.visibility = View.GONE
            ivCry3.clearAnimation()
            ivCry3.visibility = View.GONE
            ivCry4.clearAnimation()
            ivCry4.visibility = View.GONE
        }
    }
}