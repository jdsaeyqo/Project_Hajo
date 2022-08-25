package com.ssafy.hajo.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.ssafy.hajo.databinding.RequestWriteDialogBinding
import com.ssafy.hajo.entity.GrandPlan
import com.ssafy.hajo.repository.dto.response.GrandPlanResponse

class RequestWriteDialog(context: Context, private val planList: MutableList<GrandPlan>,
                         private val okCallback: (Int, Int) -> Unit): Dialog(context) {
    lateinit var binding: RequestWriteDialogBinding
    private var mylists = mutableListOf<String>()
    private var mySelected = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RequestWriteDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        setCancelable(false)

        // 뒷 배경 어둡게 처리
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        planList.forEach {
            mylists.run {
                add(it.planTitle)
            }
        }

        binding.apply {
            btnCancel.setOnClickListener {
                dismiss()
            }

            val myAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, mylists)
            planSpinner.adapter = myAdapter
            planSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    mySelected = position
//                    val date_str = planList[mySelected].grandPlanDates
//                    var smallCount = 0
//                    for(i in date_str.indices) {
//                        if(date_str[i] == '1') smallCount++
//                    }
//                    val winPoint = smallCount * 50
                    binding.tvWinPoint.setText("+100p")
                    binding.tvWinwinPoint.setText("+150p")
                    binding.picker.value = 2
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }

            // 적용 버튼 콜백
            btnRegister.setOnClickListener {
                dismiss()
                okCallback(mySelected, picker.value)
            }

            picker.apply {
                maxValue = 5
                minValue = 2
                value = 2
                wrapSelectorWheel = false
            }
        }
    }
}