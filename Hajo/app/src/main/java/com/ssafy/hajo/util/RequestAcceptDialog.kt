package com.ssafy.hajo.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.ssafy.hajo.databinding.RequestAcceptedDialogBinding

class RequestAcceptDialog(context: Context, private val okCallback: (String) -> Unit): Dialog(context) {
    lateinit var binding: RequestAcceptedDialogBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = RequestAcceptedDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }


    private fun initView() {
        setCancelable(false)

        // 뒷 배경 어둡게 처리
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.tvClose.setOnClickListener {
            dismiss()
        }

        // 적용 버튼 콜백
        binding.tvOk.setOnClickListener {
            dismiss()
            okCallback("대플랜 이름")
        }
    }
}