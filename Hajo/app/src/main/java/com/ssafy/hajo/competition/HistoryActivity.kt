package com.ssafy.hajo.competition

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.hajo.R
import com.ssafy.hajo.databinding.ActivityHistoryBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {
    lateinit var binding: ActivityHistoryBinding
    lateinit var historyAdapter: HistoryAdapter
    private val historyViewModel by viewModels<HistoryViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 화면이 위로 올라오는 Animation 효과 적용
        overridePendingTransition(R.anim.translate_left, R.anim.none)


        binding.btnClose.setOnClickListener {
            finish()
        }

        binding.rvHistory.apply {
            historyAdapter = HistoryAdapter(this@HistoryActivity, historyViewModel.history.value!!)
            adapter = historyAdapter
            layoutManager = LinearLayoutManager(this@HistoryActivity)
        }
    }

    override fun finish() {
        super.finish()
        // 화면이 아래로 내려가는 Animation 효과 적용
        overridePendingTransition(R.anim.none,R.anim.translate_right)
    }
}