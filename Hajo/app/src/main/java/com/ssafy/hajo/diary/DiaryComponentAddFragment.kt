package com.ssafy.hajo.diary

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ssafy.hajo.databinding.FragmentDiaryAddComponentBinding
import com.ssafy.hajo.databinding.FragmentPlanAddBinding

class DiaryComponentAddFragment(context: Context) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentDiaryAddComponentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiaryAddComponentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBtn()
        Log.d("diaryActivity","diaryAdd 프래그먼트 호출 완료")


    }

    private fun initBtn() {

        binding.component1.setOnClickListener { // 이미지 추가
            val activity = activity as DiaryActivity
            activity.componentAdd(1)
            val fragmentManager = requireActivity().supportFragmentManager;
            fragmentManager.beginTransaction().remove(this).commit();
        }

        binding.component2.setOnClickListener { // 텍스트 추가
            val activity = activity as DiaryActivity
            activity.componentAdd(2)
            val fragmentManager = requireActivity().supportFragmentManager;
            fragmentManager.beginTransaction().remove(this).commit();
        }
    }


}