package com.ssafy.hajo.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.hajo.databinding.ActivityFaqBinding
import com.ssafy.hajo.entity.FAQ

class FAQActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFaqBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFaqBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }

        val faqList = mutableListOf(
            FAQ(
                1,
                "하조의 플래너는 어떻게 구성되어 있나요?",
                "하조는 크게 목표, 세부 목표, 태스크로 나뉘어져 있습니다. 가장 큰 목표 아래에 세부 목표가 있고 세부목표를 달성하기 위한 할 일로 나뉘어져 있습니다!",
                false
            ),
            FAQ(
                2,
                "게시판은 어떻게 이용하나요?",
                "자랑 게시판의 경우 자신이 한 태스크나 세부 목표에 대해 달성한 내용을 업로드 해서 사람들에게 보여주는 곳입니다.공유 플랜 게시판의 경우 내가 하고 있는 플랜, 혹은 완료한 플랜 중에서 이렇게 이렇게 했다! 라고 공유 하고 싶은 목표를 올려주시면 됩니다!",
                false
            ),
            FAQ(3, "유저의 차단 및 신고는 어떻게 하나요?", "각 게시판의 해당 글에는 유저의 신고 및 게시글 차단 기능이 있습니다. 또한, 경쟁전 제안서에도 유저 신고 기능이 있습니다", false),
            FAQ(4, "경쟁전 시스템 질문", "내용4", false)
        )

        binding.rvInformation.adapter = FaqAdapter(this, faqList)
        binding.rvInformation.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

    }
}