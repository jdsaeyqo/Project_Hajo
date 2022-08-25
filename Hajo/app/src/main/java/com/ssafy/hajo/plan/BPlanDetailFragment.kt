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
import com.ssafy.hajo.databinding.FragmentPlanAddBinding

class BPlanDetailFragment (context: Context) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentBplanDetailBinding
    private val viewModel: PlanDetailViewModel by activityViewModels()

    lateinit var ctx : Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx  = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBplanDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etBplanName.setText(viewModel.bPlan1.value!!.grandplanTitle)
        binding.etBplanDetail.setText(viewModel.bPlan1.value!!.grandplanDesc)
//        binding.imgBplanColor.setBackgroundColor(R.color.primary) // todo 다이어리 색 변경 적용

        binding.btnSaveBplan.setOnClickListener {
            if(binding.etBplanName.text.toString() != viewModel.bPlan1.value!!.grandplanTitle) {
                viewModel.updateBPlantitle(viewModel.bPlan1.value!!.grandplanSeq, binding.etBplanName.text.toString())
            }

            if(binding.etBplanDetail.text.toString() != viewModel.bPlan1.value!!.grandplanDesc) {
                viewModel.updateBPlanDescription(viewModel.bPlan1.value!!.grandplanSeq, binding.etBplanDetail.text.toString())
            }

//            if(binding.imgBplanColor.background.toString() != viewModel.bPlan1.value!!.grandplanColor) {
//                viewModel.updateBPlanColor(viewModel.bPlan1.value!!.grandplanSeq, binding.imgBplanColor.background.toString())
//            }

            val fragmentManager = requireActivity().supportFragmentManager;
            fragmentManager.beginTransaction().remove(this).commit();
        }

        binding.tvDeleteBPlan.setOnClickListener {
//            val planSeq = viewModel.bPlan1.value!!.grandplanSeq
//            Log.d("planDetailDelete","planSeq : $planSeq")
//            viewModel.deleteBPlan(planSeq)
//            val fragmentManager = requireActivity().supportFragmentManager;
//            fragmentManager.beginTransaction().remove(this).commit();
            val builder = AlertDialog.Builder(ctx)
                .setTitle("플랜 삭제")
                .setMessage("정말 삭제하시겠습니까?")
                .setPositiveButton("확인",
                    DialogInterface.OnClickListener{ dialog, which ->
                        val planSeq = viewModel.bPlan1.value!!.grandplanSeq
                        Log.d("planDetailDelete","planSeq : $planSeq")
                        viewModel.deleteBPlan(planSeq)
                        val fragmentManager = requireActivity().supportFragmentManager;
                        fragmentManager.beginTransaction().remove(this).commit();
                    })
                .setNegativeButton("취소",
                    DialogInterface.OnClickListener { dialog, which ->
                        Toast.makeText(ctx, "취소", Toast.LENGTH_SHORT).show()
                    })

            builder.show()
        }

    }
}