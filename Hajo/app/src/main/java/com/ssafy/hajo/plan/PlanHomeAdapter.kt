package com.ssafy.hajo.plan

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.hajo.R
import com.ssafy.hajo.databinding.RecyclerPlanHomeBinding
import com.ssafy.hajo.repository.dto.LargePlanHomeDTO
import com.ssafy.hajo.repository.dto.response.GrandPlanHomeResponse

class PlanHomeAdapter(
    val largePlan: MutableList<GrandPlanHomeResponse>,
    val context: Context,
    val clickListener: (Long) -> Unit
) :
    RecyclerView.Adapter<PlanHomeAdapter.PlanHomeViewHolder>() {

    inner class PlanHomeViewHolder(private val binding: RecyclerPlanHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {

            binding.tvPlanHomeLargePlan.text = largePlan[position].planTitle
            binding.tvPlanHomeLargePlan.setOnClickListener {
                clickListener(largePlan[position].planSeq)

            }
//            val percent = largePlan[position].planRate
//
//            Log.d("PlanHomeAdapter", "percent: $percent")
//            when (percent) {
//                in 0..30 -> {
//                    binding.tvPlanHomeLargePlanPercent.setTextColor(
//                        ContextCompat.getColor(
//                            context,
//                            R.color.error
//                        )
//                    )
//                }
//                in 31..70 -> {
//                    binding.tvPlanHomeLargePlanPercent.setTextColor(
//                        ContextCompat.getColor(
//                            context,
//                            R.color.primary
//                        )
//                    )
//                }
//                in 71..99 -> {
//                    binding.tvPlanHomeLargePlanPercent.setTextColor(
//                        ContextCompat.getColor(
//                            context,
//                            R.color.accent
//                        )
//                    )
//                }
//                100 -> {
//                    binding.tvPlanHomeLargePlanPercent.setTextColor(
//                        ContextCompat.getColor(
//                            context,
//                            R.color.done
//                        )
//                    )
//                }
//
//            }
//            if (percent == 100) {
//                binding.tvPlanHomeLargePlanPercent.text = "완료"
//            } else {
//                binding.tvPlanHomeLargePlanPercent.text = "${percent}%"
//            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanHomeViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_plan_home, parent, false)
        return PlanHomeViewHolder(RecyclerPlanHomeBinding.bind(view))
    }

    override fun onBindViewHolder(holder: PlanHomeViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return largePlan.size
    }

    override fun getItemId(position: Int): Long {
        return largePlan[position].planSeq
    }


}