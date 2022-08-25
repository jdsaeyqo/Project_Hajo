package com.ssafy.hajo.board.share

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.transition.AutoTransition
import androidx.transition.Slide
import androidx.transition.TransitionManager
import com.bumptech.glide.Glide
import com.ssafy.hajo.R
import com.ssafy.hajo.databinding.ActivityShareDetailBinding
import com.ssafy.hajo.repository.dto.response.ShareDetailResponse
import com.ssafy.hajo.util.GlobalApplication
import com.ssafy.hajo.util.ReportPopupMenu

class ShareDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShareDetailBinding
    private val shareDetailViewModel: ShareDetailViewModel by viewModels()
    lateinit var shareDetail: ShareDetailResponse
    private val uid = GlobalApplication.userPrefs.getString("uid", "")
    var sharePlanSeq = 0L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_share_detail)


        sharePlanSeq = intent.extras?.getLong("sharePlanSeq")!!
        Log.d("ShareDetailActivity_", "onCreate: $sharePlanSeq")
        shareDetailViewModel.getShareDetail(sharePlanSeq)

        shareDetailViewModel.shareDetail.observe(this) {
            if (shareDetailViewModel.shareDetail.value != null) {
                shareDetail = it
                Log.d("ShareDetailActivity_", "it.wrtUid: ${shareDetail.wrtUid}")
                binding.apply {
                    this.sharePlan = shareDetailViewModel.shareDetail.value!!

                    if (it.wrtUid == uid) {
                        btnDeleteBoard.visibility = View.VISIBLE
                        btnModifyBoard.visibility = View.VISIBLE
                    }

                    if (!shareDetail.userIslike) {
                        btnShareDetailLike.setImageResource(R.drawable.heart_blank)

                    } else {
                        btnShareDetailLike.setImageResource(R.drawable.heart_filled)
                    }

                    if (!shareDetail.userIsbmk) {
                        btnShareDetailBookmark.setImageResource(R.drawable.ribbon)

                    } else {
                        btnShareDetailBookmark.setImageResource(R.drawable.bookmark_filled)

                    }

                    when (shareDetail.shrplanCategory) {
                        "운동" -> {
                            ivDetailCategory.setImageResource(R.drawable.fitness)
                        }
                        "스포츠" -> {
                            ivDetailCategory.setImageResource(R.drawable.sports)
                        }
                        "음악" -> {
                            ivDetailCategory.setImageResource(R.drawable.guitar)
                        }
                        "미술" -> {
                            ivDetailCategory.setImageResource(R.drawable.art)
                        }
                        "학습" -> {
                            ivDetailCategory.setImageResource(R.drawable.study)
                        }
                    }

                    Log.d("ShareDetailActivity_", "onCreate: ${shareDetail.wrtImg}")
                    Glide.with(this@ShareDetailActivity).load(shareDetail.wrtImg).fitCenter()
                        .into(ivProfileItem)

                }
            }
        }
        binding.btnDeleteBoard.setOnClickListener {
            shareDetailViewModel.deleteShare(sharePlanSeq)
            finish()
        }
        binding.btnModifyBoard.setOnClickListener {

            val intent = Intent(this, ShareAddActivity::class.java)
            intent.putExtra("mode", 1)
            shareDetail.wrtImg = ""
            intent.putExtra("share", shareDetail)
            startActivity(intent)
        }
        binding.btnDropDown.setOnClickListener {
            TransitionManager.beginDelayedTransition(
                binding.cardview,
                AutoTransition()
            )
            TransitionManager.beginDelayedTransition(
                binding.clCon,
                AutoTransition()
            )
            binding.hidenView.visibility = View.VISIBLE
            binding.btnDropDown.visibility = View.INVISIBLE
            binding.btnUp.visibility = View.VISIBLE
        }

        binding.btnUp.setOnClickListener {

            binding.hidenView.visibility = View.GONE
            binding.btnDropDown.visibility = View.VISIBLE
            binding.btnUp.visibility = View.GONE
        }

        binding.btnShareDetailLike.setOnClickListener {

        }

        val shareReport: (Long) -> Unit = { _ ->
            if (shareDetail.wrtUid != uid) {
                shareDetailViewModel.reportShare(shareDetail.shrplanSeq)
            } else {
                shareDetailViewModel.reportShare(-1L)
            }

            Log.d("ShareDetailActivity_공통", "onCreate: ${shareDetail.shrplanSeq}")
        }
        val sharBlock: (Long) -> Unit = { _ ->
            if (shareDetail.wrtUid != uid) {
                shareDetailViewModel.blockShare(shareDetail.shrplanSeq)
            } else {
                shareDetailViewModel.blockShare(-1L)
            }

            Log.d("ShareDetailActivity_공통", "onCreate: ${shareDetail.shrplanSeq}")

        }
        val userReport: (String) -> Unit = { _ ->
            if (shareDetail.wrtUid != uid) {
                shareDetailViewModel.reportUser(shareDetail.wrtUid)
            } else {
                shareDetailViewModel.reportUser("")
            }

            Log.d("ShareDetailActivity_공통", "onCreate: ${shareDetail.wrtUid}")

        }

        val userBlock: (String) -> Unit = { _ ->
            if (shareDetail.wrtUid != uid) {
                shareDetailViewModel.blockUser(shareDetail.wrtUid)
            } else {
                shareDetailViewModel.blockUser("")
            }
            Log.d("ShareDetailActivity_공통", "onCreate: ${shareDetail.wrtUid}")

        }


        binding.btnDetailMore.setOnClickListener {
            ReportPopupMenu(this, it, shareReport, sharBlock, userReport, userBlock).showPopup()
        }

        binding.btnShareDetailLike.setOnClickListener {

            if (!shareDetail.userIslike) {
                shareDetail.userIslike = true
                shareDetailViewModel.shareUpdateLike(sharePlanSeq, 1)
                binding.btnShareDetailLike.setImageResource(R.drawable.heart_filled)
                shareDetail.shrplanLikecount++
                binding.tvShareDetailLikeCnt.text = "${shareDetail.shrplanLikecount}"


            } else {
                shareDetail.userIslike = false
                shareDetailViewModel.shareUpdateLike(sharePlanSeq, 0)
                binding.btnShareDetailLike.setImageResource(R.drawable.heart_blank)
                shareDetail.shrplanLikecount--
                binding.tvShareDetailLikeCnt.text = "${shareDetail.shrplanLikecount}"
            }
        }

        binding.btnShareDetailBookmark.setOnClickListener {

            if (!shareDetail.userIsbmk) {
                shareDetail.userIsbmk = true
                shareDetailViewModel.shareUpdateBmk(sharePlanSeq, 1)
                binding.btnShareDetailBookmark.setImageResource(R.drawable.bookmark_filled)
                shareDetail.shrplanBmkcount++
                binding.tvShareDetailBookmarkCnt.text = "${shareDetail.shrplanBmkcount}"


            } else {
                shareDetail.userIsbmk = false
                shareDetailViewModel.shareUpdateBmk(sharePlanSeq, 0)
                binding.btnShareDetailBookmark.setImageResource(R.drawable.ribbon)
                shareDetail.shrplanBmkcount--
                binding.tvShareDetailBookmarkCnt.text = "${shareDetail.shrplanBmkcount}"
            }
        }

    }

    override fun onResume() {
        super.onResume()
        shareDetailViewModel.getShareDetail(sharePlanSeq)
    }
}