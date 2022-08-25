package com.ssafy.hajo.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import com.ssafy.hajo.databinding.DialogRequestPlanSelectBinding

class RequestPlanSelectDialog(context: Context, private val mylists: MutableList<String>,
                              private val okCallback: (Int) -> Unit): Dialog(context) {
    lateinit var binding: DialogRequestPlanSelectBinding
    private var mySelected = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogRequestPlanSelectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        setCancelable(false)

        // 뒷 배경 어둡게 처리
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        val myAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, mylists)
        binding.myPlanSpinner.adapter = myAdapter
        binding.myPlanSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                mySelected = position
                binding.tvWinPoint.setText("+100p")
                binding.tvWinwinPoint.setText("+150p")
                binding.picker.value = 2
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        binding.btnOk.setOnClickListener {
            okCallback(mySelected)
            dismiss()
        }

        binding.picker.apply {
            maxValue = 5
            minValue = 2
            value = 2
            wrapSelectorWheel = false
        }
    }
}