package com.ssafy.hajo.competition

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.hajo.R
import com.ssafy.hajo.databinding.ListItemPlanScheduleBinding
import com.ssafy.hajo.entity.GrandPlan
import com.ssafy.hajo.repository.dto.response.GrandPlanResponse

class GrandPlanAdapter(private val mContext: Context,
                       private val datas: MutableList<GrandPlan>):
    RecyclerView.Adapter<GrandPlanAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GrandPlanAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val binding = ListItemPlanScheduleBinding.inflate(layoutInflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GrandPlanAdapter.ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int = datas.size

    fun getData() = datas

    inner class ViewHolder(private val binding: ListItemPlanScheduleBinding):
        RecyclerView.ViewHolder(binding.root) {
            fun bind(grandPlan: GrandPlan) {
                binding.apply {
                    this.matchGrandPlan = grandPlan

//                    val arr_dates = grandPlanResponse.grandPlanDates.split(",")
//                    for(i in arr_dates.indices) {
//                        val id = mContext.resources.getIdentifier("iv_square${i+1}","id",mContext.packageName)
//
//                        val imageView = itemView.findViewById<ImageView>(id)
//                        if(arr_dates[i] == "1") {
//                            imageView.setImageResource(R.drawable.green_square)
//                        }
//                    }
                }
            }
        }
}