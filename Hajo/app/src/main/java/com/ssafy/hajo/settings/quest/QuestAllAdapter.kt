package com.ssafy.hajo.settings.quest

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.hajo.R
import com.ssafy.hajo.databinding.RecyclerQuestAllBinding
import com.ssafy.hajo.databinding.RecyclerQuestItemBinding
import com.ssafy.hajo.repository.dto.response.QuestResponse

class QuestAllAdapter : RecyclerView.Adapter<QuestAllAdapter.QuestViewHolder>() {

    var questList = mutableListOf<QuestResponse>()

    inner class QuestViewHolder(val binding: RecyclerQuestAllBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(quest: QuestResponse) {
            binding.quest = quest

            if (quest.archiveRewardtype != "title") {
                binding.tvRewardAmount.text = quest.archiveRewardseq.toString()
            } else {
                binding.tvRewardAmount.visibility = View.GONE
                binding.tvRewardHeader.visibility = View.VISIBLE
                binding.tvRewardName.visibility = View.VISIBLE
            }

            when (quest.archiveRewardtype) {
                "point" -> {
                    binding.ivRewardType.setImageResource(R.drawable.token)
                }
                "exp" -> {
                    binding.ivRewardType.setImageResource(R.drawable.exp)
                }
                "title" -> {
                    binding.ivRewardType.setImageResource(R.drawable.medal)
                }
            }

            when (quest.archiveCondparam) {
                "point" -> {
                    binding.tvQuestNeeds.text = "총 포인트 ${quest.cond} 획득 시"
                }
                "win" -> {
                    binding.tvQuestNeeds.text = "총 승리 ${quest.cond}회 시"
                }
                "winwin" -> {
                    binding.tvQuestNeeds.text = "총 WINWIN ${quest.cond}회 시"
                }
                "exp" -> {
                    binding.tvQuestNeeds.text = "총 경험치 ${quest.cond} 획득 시"
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_quest_all, parent, false)
        return QuestViewHolder(RecyclerQuestAllBinding.bind(view))
    }

    override fun onBindViewHolder(holder: QuestViewHolder, position: Int) {
        holder.bind(questList[position])
    }

    override fun getItemCount(): Int = questList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setList(questList: MutableList<QuestResponse>) {
        this.questList = questList
        notifyDataSetChanged()

    }
}