package com.ssafy.hajo.competition

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.ssafy.hajo.R
import com.ssafy.hajo.databinding.ActivityMatchBinding

class MatchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMatchBinding
    private val matchFormViewModel by viewModels<MatchFormViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMatchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().add(R.id.request_frame, MyRequestFragment()).commit()

        // 화면이 위로 올라오는 Animation 효과 적용
        overridePendingTransition(R.anim.translate_left, R.anim.none)

        // 각 탭 별 선택 리스너
        // 0 -> 제안서, 1 -> 프로필, 2 -> 프로필
        binding.tlRequest.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val position = tab?.position
                var selected: Fragment? = null

                when(position) {
                    0 -> selected = MyRequestFragment()
                    1 -> selected = RequestFragment()
                    2 -> selected = ProfileFragment()
                }
                if (selected != null) {
                    supportFragmentManager.beginTransaction().replace(R.id.request_frame, selected).commit()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        binding.btnClose.setOnClickListener {
            finish()
        }
    }

    override fun finish() {
        super.finish()
        // 화면이 아래로 내려가는 Animation 효과 적용
        overridePendingTransition(R.anim.none,R.anim.translate_right)
    }
}