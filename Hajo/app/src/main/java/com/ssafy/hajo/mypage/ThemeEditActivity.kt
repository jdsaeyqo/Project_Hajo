package com.ssafy.hajo.mypage

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.hajo.R
import com.ssafy.hajo.databinding.ActivityThemeEditBinding

class ThemeEditActivity : AppCompatActivity() {
    lateinit var binding: ActivityThemeEditBinding
    private val themeViewModel by viewModels<ThemeViewModel>()
    lateinit var themeAdapter: ThemeAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThemeEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 화면이 위로 올라오는 Animation 효과 적용
        overridePendingTransition(R.anim.translate_left, R.anim.none)

        // 다른 테마를 적용 눌렀을 때
        // 뷰모델의 리스트 업데이트 후 어댑터 업데이트
        themeAdapter = ThemeAdapter(this) { before, after ->
            themeViewModel.changeTheme(before, after)
            updateAdapter()
        }

        themeViewModel.themes.observe(this) {
            updateAdapter()
        }

        binding.apply {
            rvTheme.layoutManager = LinearLayoutManager(this@ThemeEditActivity)
            rvTheme.adapter = themeAdapter

            ivBack.setOnClickListener {
                finish()
            }
        }
    }

    override fun finish() {
        super.finish()
        // 화면이 아래로 내려가는 Animation 효과 적용
        overridePendingTransition(R.anim.none,R.anim.translate_right)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateAdapter() {
        themeAdapter.setData(themeViewModel.themes.value!!)
        themeAdapter.notifyDataSetChanged()
    }
}