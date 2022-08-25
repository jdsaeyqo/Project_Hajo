package com.ssafy.hajo.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.hajo.databinding.DialogPlanBinding
import com.ssafy.hajo.mypage.TaskAdapter
import com.ssafy.hajo.repository.dao.PlanDetailRepository
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.*

class PlanDialog(private val mContext: Context,private val seq: Long, private val today: String): Dialog(mContext) {
    lateinit var binding: DialogPlanBinding
    private val datas = mutableListOf<String>()
    lateinit var taskAdapter : TaskAdapter

    val token = GlobalApplication.userPrefs.getString("jwt","")!!
    val userId = GlobalApplication.userPrefs.getString("uid","")!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogPlanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        // 뒷 배경 어둡게 처리
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val df = SimpleDateFormat("yyyy.MM.dd")
        binding.tvDate.setText(today)

        runBlocking {
            val hm = HashMap<String, String>()
            hm["grandPlanSeq"] = seq.toString()
            hm["date"] = today

            val res = PlanDetailRepository().getTasks(token, hm)
            Log.d("PlanDialog", "getTasks : ${res.body()}")
            if(res.isSuccessful && res.body() != null) {
                val body = res.body()!!
                binding.tvGrandPlan.setText(body.grandPlanName)
                binding.tvMiddlePlan.setText(body.midPlanName)
                datas.addAll(body.tasks)
            }
        }

        taskAdapter = TaskAdapter(datas)
        binding.rvTask.adapter = taskAdapter
        binding.rvTask.layoutManager = LinearLayoutManager(mContext)

        binding.tvClose.setOnClickListener {
            dismiss()
        }
    }
}