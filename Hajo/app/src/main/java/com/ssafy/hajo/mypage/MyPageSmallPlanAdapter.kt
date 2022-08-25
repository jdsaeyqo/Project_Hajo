package com.ssafy.hajo.mypage

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.hajo.R
import com.ssafy.hajo.databinding.ListItemMyPageSmallPlanBinding
import com.ssafy.hajo.entity.WeeklyPlan
import com.ssafy.hajo.util.TodayStringUtil
import java.util.*

class MyPageSmallPlanAdapter(private val mContext: Context, private val grandPlanSeq: Long,
                             private var datas: MutableList<WeeklyPlan>,
                             private val clickListener: (Long, String) -> Unit):
    RecyclerView.Adapter<MyPageSmallPlanAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPageSmallPlanAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemMyPageSmallPlanBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyPageSmallPlanAdapter.ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int = datas.size

    inner class ViewHolder(private val binding: ListItemMyPageSmallPlanBinding):
        RecyclerView.ViewHolder(binding.root) {
            fun bind(weeklyPlan: WeeklyPlan) {
                var calendar = Calendar.getInstance()

                val firstDay = weeklyPlan.smallPlans[0].date
                val firstDate = TodayStringUtil.tDateFormat.parse(firstDay)
                calendar.time = firstDate

                // 해당 줄의 첫 날과 전 줄의 첫 날의 월을 비교하여
                // 월이 다를 경우 월이 바뀐 것이므로 월을 표시한다.
                val firstDayMonth = calendar.get(Calendar.MONTH)

                calendar.add(Calendar.DATE, -1)
                val lastMonth = calendar.get(Calendar.MONTH)
                calendar.add(Calendar.DATE, 7)
                val nextMonth = calendar.get(Calendar.MONTH)

                if(firstDayMonth != lastMonth || firstDayMonth != nextMonth) {
                    setMonth(nextMonth)
                }
                else {
                    binding.tvMonth.visibility = View.INVISIBLE
                }

                // 해당 줄의 첫 날부터 마지막 날까지 잔디를 표시한다.
                for(i in 0..6) {
                    val id = mContext.resources.getIdentifier("iv_record${i+1}", "id", mContext.packageName)
                    val imageView = itemView.findViewById<ImageView>(id)


                    // 각 날짜의 task 갯수에 따라 잔디를 다르게 표시.
                    if(i < weeklyPlan.smallPlans.size) {
                        when(weeklyPlan.smallPlans[i].counts) {
                            0 -> imageView.setImageResource(R.drawable.star0)
                            in 1..2 -> imageView.setImageResource(R.drawable.star1)
                            in 3..4 -> imageView.setImageResource(R.drawable.star2)
                            else -> imageView.setImageResource(R.drawable.star3)
                        }
                        imageView.setOnClickListener {
                            clickListener(grandPlanSeq, weeklyPlan.smallPlans[i].date)
                        }
                    }
                    // 오늘이 토요일이 아니어서 요번 주 소플랜의 갯수가 7개 아닐 경우
                    else {
                        imageView.visibility = View.INVISIBLE
                    }
                }
            }

            // 월 설정
            fun setMonth(month: Int) {
                binding.tvMonth.apply {
                    when(month) {
                        0 -> setText("Jan")
                        1 -> setText("Feb")
                        2 -> setText("Mar")
                        3 -> setText("Apr")
                        4 -> setText("May")
                        5 -> setText("Jun")
                        6 -> setText("Jul")
                        7 -> setText("Aug")
                        8 -> setText("Sep")
                        9 -> setText("Oct")
                        10 -> setText("Nov")
                        else -> setText("Dec")
                    }
                }
            }
        }
}