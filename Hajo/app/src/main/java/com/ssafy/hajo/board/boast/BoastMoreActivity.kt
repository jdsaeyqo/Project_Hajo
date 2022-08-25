package com.ssafy.hajo.board.boast

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.hajo.databinding.ActivityBoardMoreBinding
import com.ssafy.hajo.repository.dto.response.BoastMoreResponse
import com.ssafy.hajo.util.GlobalApplication

class BoastMoreActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBoardMoreBinding

    private val boastMoreViewModel: BoastMoreViewModel by viewModels()
    private val boastViewModel: BoastViewModel by viewModels()

    private lateinit var boardMoreAdapter: BoastMoreAdapter

    val uid = GlobalApplication.userPrefs.getString("uid", "")

    var type = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoardMoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mainTitle = intent?.extras?.get("mainTitle").toString()
        binding.tvMoreMainTitle.text = mainTitle

        type = when (mainTitle) {
            "인기글" -> {
                "popular"
            }
            "최신글" -> {
                "recent"
            }
            "내가 좋아요 한 글" -> {
                "like"
            }
            "내가 작성한 글" -> {
                "my"
            }
            else -> {
                ""
            }
        }

        boastMoreViewModel.getBoastMoreList(uid!!, type)

        boastMoreViewModel.boastMoreList.observe(this) {
            if (boastMoreViewModel.boastMoreList.value != null) {
                boardMoreAdapter.setList(boastMoreViewModel.boastMoreList.value!!)
            }
        }

        val delete: (Long) -> Unit = { boastSeq ->
            boastViewModel.deleteBoast(boastSeq)
            finish()
        }

        val modify: (BoastMoreResponse) -> Unit = {
            val intent = Intent(this, BoastAddActivity::class.java)
            intent.putExtra("boastMore", it)
            intent.putExtra("mode", 2)
            startActivity(intent)
        }

        val boastReport: (Long) -> Unit = { boastSeq ->
            boastViewModel.reportBoast(boastSeq)
            Log.d("BoastMoreActivity_공통", "onCreate: $boastSeq")
        }
        val boastBlock: (Long) -> Unit = { boastSeq ->
            boastViewModel.blockBoast(boastSeq)
            Log.d("BoastMoreActivity_공통", "onCreate: $boastSeq")

        }
        val userReport: (String) -> Unit = { wrtUid ->
            boastViewModel.reportUser(wrtUid)
            Log.d("BoastMoreActivity_공통", "onCreate: $wrtUid")

        }
        val userBlock: (String) -> Unit = { wrtUid ->
            boastViewModel.blockUser(wrtUid)
            Log.d("BoastMoreActivity_공통", "onCreate: $wrtUid")

        }

        boardMoreAdapter =
            BoastMoreAdapter(this, delete, modify, boastReport, boastBlock, userReport, userBlock)
        boardMoreAdapter.likeClickListener = object : BoastMoreAdapter.LikeClickListener {
            override fun clickLike(boastSeq: Long, mode: Int) {
                boastViewModel.boastUpdateLike(boastSeq, mode)
            }

        }
        binding.rvBoardMore.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvBoardMore.adapter = boardMoreAdapter

        binding.btnBack.setOnClickListener {
            finish()
        }

    }

    override fun onResume() {
        super.onResume()
        boastMoreViewModel.getBoastMoreList(uid!!, type)
    }
}