package com.ssafy.hajo.settings.quest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.ssafy.hajo.databinding.ActivityQuestBinding

class QuestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuestBinding
    private val tabLayoutTitle = arrayOf("전체", "내 현황")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.viewPager.adapter = QuestViewPagerAdapter(supportFragmentManager, lifecycle)

        TabLayoutMediator(binding.tlQuest, binding.viewPager) { tab, position ->
            tab.text = tabLayoutTitle[position]
        }.attach()


    }
}