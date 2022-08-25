package com.ssafy.hajo.board.share

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ssafy.hajo.R
import com.ssafy.hajo.databinding.ActivityShareAddBinding
import com.ssafy.hajo.entity.GrandPlan
import com.ssafy.hajo.plan.BPlanSpinner
import com.ssafy.hajo.repository.dto.request.ShareAddRequest
import com.ssafy.hajo.repository.dto.request.ShareModifyRequest
import com.ssafy.hajo.repository.dto.response.ShareDetailResponse
import com.ssafy.hajo.util.GlobalApplication

class ShareAddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShareAddBinding
    private val viewModel: ShareAddViewModel by viewModels()
    lateinit var grandPlanList: MutableList<BPlanSpinner>
    lateinit var grandPlanStringList: Array<String>
    var sharePlanSeq = 0L
    val uid = GlobalApplication.userPrefs.getString("uid", "")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_share_add)

        val mode = intent?.extras?.getInt("mode")
        val share = intent?.extras?.get("share")

        viewModel.getGrandPlanList()

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.vm = viewModel
        binding.lifecycleOwner = this

        if (share != null) {
            val shareDetail = share as ShareDetailResponse
            sharePlanSeq = shareDetail.shrplanSeq
            viewModel.setData(shareDetail)
        }

        binding.btnSelectCategory.setOnClickListener {
            binding.btnSelectCategory.text = "요리"
        }


        binding.btnSelectGrandPlan.setOnClickListener {
            showListDialog(0)
        }


        binding.btnSelectCategory.setOnClickListener {
            showListDialog(1)
        }

        viewModel.validate.observe(this) {
            if (viewModel.validate.value == false) {
                Toast.makeText(this, "빈 칸을 채워주세요", Toast.LENGTH_SHORT).show()

            } else {

                var grandPlanSeq = 0L
                val grandPlanTitle = viewModel.shareGrandPlanTitle.value
                grandPlanList.forEach {
                    if (it.planTitle == grandPlanTitle) {
                        grandPlanSeq = it.planSeq.toLong()
                        return@forEach
                    }
                }
                val sharePlanTitle = viewModel.sharePlanTitle.value!!
                val sharePlanContent = viewModel.sharePlanContent.value!!
                val sharePlanCategory = viewModel.sharePlanCategory.value!!
                val shareSpendingPeriod = viewModel.shareSpendingPeriod.value!!
                val shareGrandSummary = viewModel.shareGrandSummary.value!!
                val shareAddRequest = ShareAddRequest(
                    uid!!,
                    grandPlanSeq,
                    sharePlanSeq,
                    sharePlanTitle,
                    sharePlanContent,
                    sharePlanCategory,
                    shareSpendingPeriod,
                    shareGrandSummary
                )
                viewModel.upload(shareAddRequest, mode!!)
                finish()
            }
        }

        viewModel.sharedGrandPlanList.observe(this) {
            if (viewModel.sharedGrandPlanList.value != null) {

                grandPlanList = viewModel.sharedGrandPlanList.value!!
                grandPlanStringList = Array(grandPlanList.size) { "" }
                for (i in grandPlanList.indices) {
                    grandPlanStringList[i] = grandPlanList[i].planTitle
                }


            }

        }

    }

    private fun showListDialog(flag: Int) {

        when (flag) {
            0 -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("대플랜을 선택해 주세요")
                builder.setItems(
                    grandPlanStringList
                ) { builder, which ->
                    binding.tvShareAddGrandPlan.text = grandPlanStringList[which]
                }

                val dialog: AlertDialog = builder.create()
                dialog.show()
            }

            1 -> {
                val categoryList = arrayOf("학습", "스포츠", "음악", "미술", "운동")
                val builder = AlertDialog.Builder(this)
                builder.setTitle("카테고리를 선택해 주세요")
                builder.setItems(
                    categoryList
                ) { builder, which ->
                    binding.btnSelectCategory.text = categoryList[which]
                }

                val dialog: AlertDialog = builder.create()
                dialog.show()
            }
        }
    }


}