package com.ssafy.hajo.plan

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ssafy.hajo.R
import com.ssafy.hajo.databinding.FragmentBplanDetailBinding
import com.ssafy.hajo.databinding.FragmentMplanDetailBinding

class MPlanDetailFragment (context: Context) : BottomSheetDialogFragment() {
    private val mContext: Context = context
    private lateinit var binding: FragmentMplanDetailBinding
    private val viewModel: PlanDetailViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMplanDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("mplandetail", "mplandetail 진입 ${viewModel.bPlansForSpinner.value}")

        val mplan = viewModel.littlePlanInfo.value
        Log.d("mplandetail", "mplan ${mplan}")
        binding.etMplanName.setText(mplan!!.midplanTitle)
        binding.etMplanDetail.setText(mplan!!.midplanDesc)
        binding.imgMplanColor.setBackgroundColor(R.color.primary) //todo 다이어리 색 변경 적용


        binding.btnSaveMplan.setOnClickListener {
            if(binding.etMplanName.text.toString() != mplan!!.midplanTitle) {
                viewModel.updateMPlan(mplan.midplanSeq.toString(), binding.etMplanName.text.toString(), "title")
            }

            if(binding.etMplanDetail.text.toString() != mplan!!.midplanDesc) {
                viewModel.updateMPlan(mplan.midplanSeq.toString(), binding.etMplanDetail.text.toString(), "desc")
            }

            if(binding.imgMplanColor.background.toString() != mplan!!.midplanColor) {
//                viewModel.updateMPlan(mplan.midplanSeq.toString(), binding.imgMplanColor.background.toString() "color")
            }

            val fragmentManager = requireActivity().supportFragmentManager;
            fragmentManager.beginTransaction().remove(this).commit();
        }

        //todo 하기
        binding.tvDeleteMPlan.setOnClickListener {
//            val planSeq = viewModel.bPlan1.value!!.grandplanSeq
//            Log.d("planDetailDelete","planSeq : $planSeq")
//            viewModel.deleteBPlan(planSeq)
            val fragmentManager = requireActivity().supportFragmentManager;
            fragmentManager.beginTransaction().remove(this).commit();
            val builder = AlertDialog.Builder(requireActivity())
                .setTitle("중플랜 삭제")
                .setMessage("정말 삭제하시겠습니까?")
                .setPositiveButton("확인",
                    DialogInterface.OnClickListener{ dialog, which ->
                        val planSeq = viewModel.littlePlanInfo.value!!.midplanSeq
                        viewModel.deleteMPlan(planSeq)
                    })
                .setNegativeButton("취소",
                    DialogInterface.OnClickListener { dialog, which ->
                        Toast.makeText(this.mContext, "취소", Toast.LENGTH_SHORT).show()
                    })

            builder.show()
        }

    }
}