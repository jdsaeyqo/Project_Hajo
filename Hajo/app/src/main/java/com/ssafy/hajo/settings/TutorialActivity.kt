package com.ssafy.hajo.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.hajo.R
import com.ssafy.hajo.databinding.ActivityTutorialBinding
import com.ssafy.hajo.entity.Tutorial

class TutorialActivity : AppCompatActivity() {
    lateinit var binding: ActivityTutorialBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTutorialBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }

        val tutorialList = mutableListOf(
            Tutorial(R.drawable.tutorial1, "플로팅 버튼의 달력 버튼을 눌러 대플랜 생성 페이지로 이동할 수 있습니다!"),
            Tutorial(R.drawable.tutorial2, "중플랜 생성 버튼을 눌러 기간을 설정하고 원하는 색상을 입히세요!"),
            Tutorial(R.drawable.tutorial3, "각 날짜를 클릭해 태스크를 생성할 수 있고 메모와 알람, 이미지를 설정할 수 있습니다!"),
            Tutorial(R.drawable.tutorial4, "플랜 홈 버튼에서 공유플랜 버튼을 통해 공유 플랜 게시판으로 이동할 수 있습니다! "),

        )

        binding.vpTutorial.adapter = TutorialAdapter(tutorialList)
        binding.circleIndicator.setViewPager(binding.vpTutorial)
    }
}