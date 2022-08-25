package com.ssafy.hajo.mypage

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.ssafy.hajo.R
import com.ssafy.hajo.databinding.ActivityTitleEditBinding
import com.ssafy.hajo.repository.dto.response.TitleResponse

class TitleEditActivity : AppCompatActivity() {
    lateinit var binding: ActivityTitleEditBinding
    lateinit var titleAdapter: TitleAdapter
    private val titleViewModel by viewModels<TitleViewModel>()

    val RESULT_TITLE_OK = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTitleEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this@TitleEditActivity.titleViewModel.getTitles()

        // 화면이 위로 올라오는 Animation 효과 적용
        overridePendingTransition(R.anim.translate_left, R.anim.none)

        titleAdapter = TitleAdapter(this) { before, after ->
            titleViewModel.changeTitle(before, after)
            updateAdapter()
        }

        titleViewModel.titles.observe(this) {
            updateAdapter()
        }

        binding.apply {
            rvTitle.layoutManager = LinearLayoutManager(this@TitleEditActivity)
            rvTitle.addItemDecoration(DividerItemDecoration(this@TitleEditActivity, 1))
            rvTitle.adapter = titleAdapter

            ivBack.setOnClickListener {
                val intent = Intent(this@TitleEditActivity, UserInfoEditActivity::class.java)
                val str = Gson().toJson(this@TitleEditActivity.titleViewModel.title, TitleResponse::class.java)
                intent.putExtra("title",str)
                setResult(RESULT_TITLE_OK, intent)
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
        titleAdapter.setData(titleViewModel.titles.value!!)
        titleAdapter.notifyDataSetChanged()
    }
}