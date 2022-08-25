package com.ssafy.hajo.plan

import DiaryObject
import DiaryViewModel
import TextObjectInfo
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ssafy.hajo.databinding.FragmentEditColorBinding
import com.ssafy.hajo.databinding.FragmentEditTextBinding

class EditColorFragment(context: Context) : BottomSheetDialogFragment() {
    private val mContext: Context = context
    private lateinit var binding: FragmentEditColorBinding
    private val viewModel: PlanDetailViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditColorBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(viewModel.mPlanColor.value == null) {
            val fragmentManager = requireActivity().supportFragmentManager;
            fragmentManager.beginTransaction().remove(this).commit();
        }


        initColorButton()


    }

    private fun initColorButton() {

        binding.imgRed.setOnClickListener {
            viewModel.setMPlanColor("#F0D8887D")
            val fragmentManager = requireActivity().supportFragmentManager;
            fragmentManager.beginTransaction().remove(this).commit();
        }

        binding.imgBlack.setOnClickListener {
            viewModel.setMPlanColor("#FF5722")
            val fragmentManager = requireActivity().supportFragmentManager;
            fragmentManager.beginTransaction().remove(this).commit();
        }

        binding.imgBlue.setOnClickListener {
            viewModel.setMPlanColor("#FF7783BF")
            val fragmentManager = requireActivity().supportFragmentManager;
            fragmentManager.beginTransaction().remove(this).commit();
        }

        binding.imgPrimary.setOnClickListener {
            viewModel.setMPlanColor("#FFF5B51D")
            val fragmentManager = requireActivity().supportFragmentManager;
            fragmentManager.beginTransaction().remove(this).commit();
        }

        binding.imgGreen.setOnClickListener {
            viewModel.setMPlanColor("#FF8BB686")
            val fragmentManager = requireActivity().supportFragmentManager;
            fragmentManager.beginTransaction().remove(this).commit();
        }

        binding.imgLightBlue.setOnClickListener {
            viewModel.setMPlanColor("#74C1FD")
            val fragmentManager = requireActivity().supportFragmentManager;
            fragmentManager.beginTransaction().remove(this).commit();        }
    }

}