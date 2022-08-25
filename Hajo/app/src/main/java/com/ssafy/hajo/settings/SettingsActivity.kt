package com.ssafy.hajo.settings

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.hajo.R
import com.ssafy.hajo.databinding.ActivitySettingsBinding
import com.ssafy.hajo.settings.quest.QuestActivity

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // 화면이 위로 올라오는 Animation 효과 적용
        overridePendingTransition(R.anim.translate_left, R.anim.none)

        binding.llStore.setOnClickListener {
            val intent = Intent(this, StoreActivity::class.java)
            startActivity(intent)

        }
        binding.llTutorial.setOnClickListener {
            val intent = Intent(this, TutorialActivity::class.java)
            startActivity(intent)

        }
        binding.llFaq.setOnClickListener {
            val intent = Intent(this, FAQActivity::class.java)
            startActivity(intent)

        }
        binding.llQuest.setOnClickListener {
            val intent = Intent(this, QuestActivity::class.java)
            startActivity(intent)

        }

        binding.btnBack.setOnClickListener {
            finish()
        }


    }

    override fun finish() {
        super.finish()
        // 화면이 아래로 내려가는 Animation 효과 적용
        overridePendingTransition(R.anim.none,R.anim.translate_right)
    }
}