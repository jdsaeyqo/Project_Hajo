package com.ssafy.hajo.mypage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ssafy.hajo.R
import com.ssafy.hajo.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 화면이 위로 올라오는 Animation 효과 적용
        overridePendingTransition(R.anim.translate_left, R.anim.none)
    }

    override fun finish() {
        super.finish()
        // 화면이 아래로 내려가는 Animation 효과 적용
        overridePendingTransition(R.anim.none,R.anim.translate_right)
    }
}