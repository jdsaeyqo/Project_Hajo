package com.ssafy.hajo.board.boast

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuInflater
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.ssafy.hajo.R
import com.ssafy.hajo.databinding.ActivityBoardDetailBinding
import com.ssafy.hajo.repository.dto.response.BoastDetailResponse
import com.ssafy.hajo.util.GlobalApplication
import com.ssafy.hajo.util.ReportPopupMenu

class BoastDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBoardDetailBinding
    private val userPrefs = GlobalApplication.userPrefs
    private val boastDetailViewModel: BoastDetailViewModel by viewModels()
    private val boastViewModel: BoastViewModel by viewModels()

    lateinit var boastDetail: BoastDetailResponse
    var boastSeq: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoardDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        boastSeq = intent?.extras?.getLong("boastSeq")!!
        boastDetailViewModel.getBoastDetail(boastSeq)

        val uid = userPrefs.getString("uid", "")

        boastDetailViewModel.boastDetail.observe(this) { boast ->
            if (boastDetailViewModel.boastDetail.value != null) {
                boastDetail = boast

                if (uid == boastDetail.wrtUid) {
                    binding.btnDeleteBoard.visibility = View.VISIBLE
                    binding.btnModifyBoard.visibility = View.VISIBLE
                }

                binding.apply {
                    this.boast = boastDetail


                    Glide.with(this@BoastDetailActivity).load(boastDetail.boastImg).centerCrop()
                        .into(ivDetailImage)
                    Glide.with(this@BoastDetailActivity).load(boastDetail.wrtImg).fitCenter()
                        .into(ivDetailProfileItem)
                }

                if (!boastDetail.userIslike) {
                    binding.btnDetailLike.setImageResource(R.drawable.heart_blank)

                } else {
                    binding.btnDetailLike.setImageResource(R.drawable.heart_filled)
                }
            }
        }



        binding.btnDeleteBoard.setOnClickListener {
            boastViewModel.deleteBoast(boastSeq)
            finish()
        }

        binding.btnModifyBoard.setOnClickListener {
            val intent = Intent(this, BoastAddActivity::class.java)
            intent.putExtra("boast", boastDetail)
            intent.putExtra("mode", 1)
            startActivity(intent)
            finish()
        }


        binding.btnBack.setOnClickListener {
            finish()
        }

        val boastReport: (Long) -> Unit = { _ ->
            if(boastDetail.wrtUid != uid){
                boastViewModel.reportBoast(boastDetail.boastSeq)
            }else{
                boastViewModel.reportBoast(-1L)
            }

            Log.d("BoastDetailActivity_공통", "onCreate: ${boastDetail.boastSeq}")
        }
        val boastBlock: (Long) -> Unit = { _ ->
            if(boastDetail.wrtUid != uid){
                boastViewModel.blockBoast(boastDetail.boastSeq)
            }else{
                boastViewModel.blockBoast(-1L)
            }

            Log.d("BoastDetailActivity_공통", "onCreate: ${boastDetail.boastSeq}")

        }
        val userReport: (String) -> Unit = { _ ->
            if(boastDetail.wrtUid != uid){
                boastViewModel.reportUser(boastDetail.wrtUid)
            }else{
                boastViewModel.reportUser("")
            }

            Log.d("BoastDetailActivity_공통", "onCreate: ${boastDetail.wrtUid}")

        }

        val userBlock: (String) -> Unit = { _ ->
            if(boastDetail.wrtUid != uid){
                boastViewModel.blockUser(boastDetail.wrtUid)
            }else{
                boastViewModel.blockUser("")
            }
            Log.d("BoastDetailActivity_공통", "onCreate: ${boastDetail.wrtUid}")

        }

        binding.btnDetailMore.setOnClickListener {
            ReportPopupMenu(this, it, boastReport, boastBlock, userReport, userBlock).showPopup()
        }


        binding.btnDetailLike.setOnClickListener {

            if (!boastDetail.userIslike) {
                boastDetail.userIslike = true
                boastViewModel.boastUpdateLike(boastSeq, 1)
                binding.btnDetailLike.setImageResource(R.drawable.heart_filled)
                boastDetail.boastLikecount++
                binding.tvDetailLikeCnt.text = "${boastDetail.boastLikecount}"


            } else {
                boastDetail.userIslike = false
                boastViewModel.boastUpdateLike(boastSeq, 0)
                binding.btnDetailLike.setImageResource(R.drawable.heart_blank)
                boastDetail.boastLikecount--
                binding.tvDetailLikeCnt.text = "${boastDetail.boastLikecount}"
            }
        }

    }


}