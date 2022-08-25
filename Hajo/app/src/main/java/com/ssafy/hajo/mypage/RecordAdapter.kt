package com.ssafy.hajo.mypage

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.hajo.databinding.ListItemRecordBinding
import com.ssafy.hajo.entity.WeeklyPlan
import com.ssafy.hajo.repository.dao.PlanDetailRepository
import com.ssafy.hajo.repository.dto.response.GrandPlanResponse
import com.ssafy.hajo.repository.dto.response.MyPageSmallPlanResponse
import com.ssafy.hajo.util.GlobalApplication
import com.ssafy.hajo.util.PlanDialog
import com.ssafy.hajo.util.TodayStringUtil
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class RecordAdapter(private val mContext: Context): RecyclerView.Adapter<RecordAdapter.ViewHolder>() {
    private var datas = emptyList<GrandPlanResponse>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val binding = ListItemRecordBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecordAdapter.ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int = datas.size

    fun setData(datas: MutableList<GrandPlanResponse>) {
        this.datas = datas
    }

    inner class ViewHolder(private val binding: ListItemRecordBinding):
        RecyclerView.ViewHolder(binding.root) {
            fun bind(grandPlanResponse: GrandPlanResponse) {
                binding.apply {
                    this.grandPlan = grandPlanResponse

                    var weekPlans = mutableListOf<WeeklyPlan>()

                    // 서버에서 MyPageSmallPlanResponse 받아온다.
                    val token = GlobalApplication.userPrefs.getString("jwt","")!!
                    val userId = GlobalApplication.userPrefs.getString("uid","")!!

                    val smallPlans = mutableListOf<MyPageSmallPlanResponse>()
                    val tDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale("ko", "KR"))
                    val cFormat = SimpleDateFormat("D")


                    runBlocking {
                        val hm = HashMap<String, String>()
                        hm["grandPlanSeq"] = grandPlanResponse.planSeq.toString()
                        hm["endDate"] = tDateFormat.format(Date())

                        val calendar = Calendar.getInstance()
                        calendar.time = Date()
                        val days = 104 + calendar.get(Calendar.DAY_OF_WEEK)

                        // 최대 105개
                        calendar.add(Calendar.DATE, -days)
                        hm["startDate"] = tDateFormat.format(calendar.time)
                        Log.d("RecordAdapter", "hashMap: ${hm}")

                        val res = PlanDetailRepository().getTaskCount(token,hm)
                        Log.d("RecordAdapter", "smallPlans: ${res.body()}")
                        if(res.isSuccessful && res.body() != null) {
                            val body = res.body()!!
                            smallPlans.addAll(body)
                        }

                        var testPlans = mutableListOf<MyPageSmallPlanResponse>()
                        var index = 0

                        for(i in 0..days) {
                            // 7일치 소플랜을 리스트에 추가
                            if(i % 7 == 0) {
                                if(testPlans.isNotEmpty()) {
                                    val tmp = mutableListOf<MyPageSmallPlanResponse>()
                                    testPlans.forEach {
                                        tmp.add(it)
                                    }
                                    weekPlans.add(WeeklyPlan(tmp))
                                    testPlans.clear()
                                }
                            }

                            if(index < smallPlans.size && smallPlans[index].date == tDateFormat.format(calendar.time)) {
                                testPlans.add(smallPlans[index])
                                index++
                            }
                            else {
                                testPlans.add(MyPageSmallPlanResponse(tDateFormat.format(calendar.time), 0))
                            }
                            // 다음 날
                            calendar.add(Calendar.DATE, 1)
                        }

                        // 마지막으로 남은 소플랜들 추가
                        if(testPlans.isNotEmpty()) {
                            weekPlans.add(WeeklyPlan(testPlans))
                        }

                        Log.d("RecordAdapter", "weekPlans : $weekPlans")

                        rvWeekPlan.adapter = MyPageSmallPlanAdapter(mContext, grandPlanResponse.planSeq, weekPlans)
                        { seq, today ->
                            Log.d("tttt", "clicked : $seq, $today")
                            // 해당 날짜에 대한 플랜 다이얼로그 표시
                            PlanDialog(mContext,seq, today).show()
                        }
                        rvWeekPlan.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
                    }

                }
            }
        }
}