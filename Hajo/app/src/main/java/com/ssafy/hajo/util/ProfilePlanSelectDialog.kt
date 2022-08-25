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
import com.ssafy.hajo.databinding.DialogProfilePlanSelectBinding
import com.ssafy.hajo.entity.GrandPlan
import com.ssafy.hajo.repository.dto.response.GrandPlanResponse

class ProfilePlanSelectDialog(context: Context, private val myPlanLists: MutableList<GrandPlan>,
                              private val enemyPlanLists: MutableList<GrandPlan>,
                              private val okCallback: (Int, Int, Int) -> Unit): Dialog(context) {
    lateinit var binding: DialogProfilePlanSelectBinding
    private var mylists = mutableListOf<String>()
    private var enemylists = mutableListOf<String>()
    private var enemySelected = 0
    private var mySelected = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogProfilePlanSelectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        setCancelable(false)

        myPlanLists.forEach {
            mylists.run {
                add(it.planTitle)
            }
        }

        enemyPlanLists.forEach {
            enemylists.run {
                add(it.planTitle)
            }
        }

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
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        val enemyAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, enemylists)
        binding.enemtyPlanSpinner.adapter = enemyAdapter
        binding.enemtyPlanSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                enemySelected = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }

        binding.btnOk.setOnClickListener {
            okCallback(mySelected, enemySelected, binding.picker.value - 1)
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