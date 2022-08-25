package com.ssafy.hajo.board.share

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.hajo.databinding.ActivityShareMoreBinding
import com.ssafy.hajo.repository.dto.response.ShareResponse
import com.ssafy.hajo.util.RecyclerItemDecoration

class ShareMoreActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShareMoreBinding
    private lateinit var shareMoreList: ArrayList<ShareResponse>
    private lateinit var shareAdapter: ShareAdapter

    private val shareMoreViewModel: ShareMoreViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShareMoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mainTitle = intent?.extras?.get("mainTitle").toString()
        binding.tvMoreMainTitle.text = mainTitle

        binding.btnBack.setOnClickListener {
            finish()
        }

        initAdapter()
        when (mainTitle) {
            "북마크 한 글" -> {
                //서버에서 받아오기
                shareMoreViewModel.getShareBookMarkList()

                shareMoreViewModel.shareBookMareList.observe(this) {
                    if (shareMoreViewModel.shareBookMareList != null) {
                        shareAdapter.setList(shareMoreViewModel.shareBookMareList.value!!)
                    }
                }

            }
            else -> {
                shareMoreList = intent?.getParcelableArrayListExtra("list")!!
                shareAdapter.setList(shareMoreList)
            }
        }

    }

    private fun initAdapter() {
        shareAdapter = ShareAdapter(1) {
            val intent = Intent(this, ShareDetailActivity::class.java)
            intent.putExtra("sharePlanSeq", it)
            startActivity(intent)

        }

        binding.rvShareMore.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvShareMore.adapter = shareAdapter
        binding.rvShareMore.addItemDecoration(RecyclerItemDecoration(30, 0, 0, 0))
    }

    override fun onResume() {
        super.onResume()
        shareMoreViewModel.getShareBookMarkList()
    }
}