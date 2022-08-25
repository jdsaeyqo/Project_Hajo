package com.ssafy.hajo.board.share

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.hajo.databinding.ActivityShareBoardBinding
import com.ssafy.hajo.repository.dto.response.ShareResponse
import com.ssafy.hajo.util.RecyclerItemDecoration

class ShareBoardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShareBoardBinding

    private val shareBoardViewModel: ShareBoardViewModel by viewModels()

    private lateinit var sharePopularAdapter: ShareAdapter
    private lateinit var shareRecentAdapter: ShareAdapter
    private lateinit var shareLikeAdapter: ShareAdapter
    private lateinit var shareMyAdapter: ShareAdapter

    private lateinit var sharePopularList: ArrayList<ShareResponse>
    private lateinit var shareRecentList: ArrayList<ShareResponse>
    private lateinit var shareLikeList: ArrayList<ShareResponse>
    private lateinit var shareMyList: ArrayList<ShareResponse>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShareBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        shareBoardViewModel.getShareTop5()

        initAdapter()
        initClickListener()

        binding.btnAddShare.setOnClickListener {
            val intent = Intent(this, ShareAddActivity::class.java)
            intent.putExtra("mode", 0)
            startActivity(intent)
        }

        shareBoardViewModel.sharePopularList.observe(this) {
            if (shareBoardViewModel.sharePopularList != null) {
                sharePopularList = shareBoardViewModel.sharePopularList.value!!

                sharePopularList.forEach {
                    it.wrtTitle = "<${it.wrtTitle}>"
                }
                sharePopularAdapter.setList(sharePopularList)
            }
        }

        shareBoardViewModel.shareRecentList.observe(this) {
            if (shareBoardViewModel.shareRecentList != null) {
                shareRecentList = shareBoardViewModel.shareRecentList.value!!
                shareRecentList.forEach {
                    it.wrtTitle = "<${it.wrtTitle}>"
                }
                shareRecentAdapter.setList(shareRecentList)
            }
        }

        shareBoardViewModel.shareLikeList.observe(this) {
            if (shareBoardViewModel.shareLikeList != null) {
                shareLikeList = shareBoardViewModel.shareLikeList.value!!
                shareLikeList.forEach {
                    it.wrtTitle = "<${it.wrtTitle}>"
                }
                shareLikeAdapter.setList(shareLikeList)
            }
        }

        shareBoardViewModel.shareMyList.observe(this) {
            if (shareBoardViewModel.sharePopularList != null) {
                shareMyList = shareBoardViewModel.shareMyList.value!!
                shareMyList.forEach {
                    it.wrtTitle = "<${it.wrtTitle}>"
                }
                shareMyAdapter.setList(shareMyList)
            }
        }

    }



    private fun initClickListener() {

        binding.tvPopularShareMore.setOnClickListener {
            val intent = Intent(this, ShareMoreActivity::class.java)
            intent.putExtra("mainTitle", "인기글")
            intent.putExtra("list", sharePopularList)
            startActivity(intent)

        }
        binding.tvRecentShareMore.setOnClickListener {
            val intent = Intent(this, ShareMoreActivity::class.java)
            intent.putExtra("mainTitle", "최신글")
            intent.putExtra("list", shareRecentList)
            startActivity(intent)
        }
        binding.tvLikeShareMore.setOnClickListener {
            val intent = Intent(this, ShareMoreActivity::class.java)
            intent.putExtra("mainTitle", "내가 좋아요 한 글")
            intent.putExtra("list", shareLikeList)
            startActivity(intent)
        }
        binding.tvMyShareMore.setOnClickListener {
            val intent = Intent(this, ShareMoreActivity::class.java)
            intent.putExtra("mainTitle", "내가 작성한 글")
            intent.putExtra("list", shareMyList)
            startActivity(intent)
        }
    }

    private fun initAdapter() {
        sharePopularAdapter = ShareAdapter(0) {
            val intent = Intent(this, ShareDetailActivity::class.java)
            intent.putExtra("sharePlanSeq", it)
            startActivity(intent)
        }
        shareRecentAdapter = ShareAdapter(0) {
            val intent = Intent(this, ShareDetailActivity::class.java)
            intent.putExtra("sharePlanSeq", it)
            startActivity(intent)
        }
        shareLikeAdapter = ShareAdapter(0) {
            Log.d("shareLikeAdapter", "initAdapter: $it")
            val intent = Intent(this, ShareDetailActivity::class.java)
            intent.putExtra("sharePlanSeq", it)
            startActivity(intent)
        }
        shareMyAdapter = ShareAdapter(0) {
            Log.d("shareMyAdapter", "initAdapter: $it")
            val intent = Intent(this, ShareDetailActivity::class.java)
            intent.putExtra("sharePlanSeq", it)
            startActivity(intent)
        }

        val decoration = RecyclerItemDecoration(0, 0, 0, 30)
        binding.rvPopularShare.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvPopularShare.adapter = sharePopularAdapter

        binding.rvPopularShare.addItemDecoration(decoration)

        binding.rvRecentShare.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvRecentShare.adapter = shareRecentAdapter

        binding.rvRecentShare.addItemDecoration(decoration)

        binding.rvLikeShare.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvLikeShare.adapter = shareLikeAdapter

        binding.rvLikeShare.addItemDecoration(decoration)

        binding.rvMyShare.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvMyShare.adapter = shareMyAdapter

        binding.rvMyShare.addItemDecoration(decoration)
    }

    override fun onStart() {
        super.onStart()
        shareBoardViewModel.getShareTop5()
    }

//    override fun onResume() {
//        super.onResume()
//        shareBoardViewModel.getShareTop5()
//    }
}