package com.ssafy.hajo.settings.quest

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.hajo.R
import com.ssafy.hajo.databinding.RecyclerQuestItemBinding
import com.ssafy.hajo.repository.dto.response.MyQuestResponse

class MyQuestAdapter(val context: Context, val receivedListener: (Long) -> Unit) :
    RecyclerView.Adapter<MyQuestAdapter.QuestViewHolder>() {

    var questList = mutableListOf<MyQuestResponse>()

    inner class QuestViewHolder(val binding: RecyclerQuestItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(quest: MyQuestResponse) {
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

            binding.pgQuestProcess.max = quest.cond
            binding.pgQuestProcess.progress = quest.usercond
            binding.tvQuestProcess.text = "${quest.usercond} / ${quest.cond}"

            if (!quest.userArchiveIsdone) {
                binding.tvQuestDone.background =
                    ContextCompat.getDrawable(context, R.drawable.bg_quest_ing)
                binding.tvQuestDone.text = "진행중"
            } else if (quest.userArchiveIsdone && !quest.userArchiveIsreceived) {
                binding.tvQuestDone.isClickable = true
                binding.tvQuestDone.background =
                    ContextCompat.getDrawable(context, R.drawable.bg_quest_done)
                binding.tvQuestDone.text = "수령"
            } else {
                binding.tvQuestDone.background =
                    ContextCompat.getDrawable(context, R.drawable.bg_quest_received)
                binding.tvQuestDone.text = "획득 완료"
            }

            binding.tvQuestDone.setOnClickListener {
                receivedListener(quest.userArchiveSeq)
                binding.tvQuestDone.background =
                    ContextCompat.getDrawable(context, R.drawable.bg_quest_received)
                binding.tvQuestDone.text = "획득 완료"
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_quest_item, parent, false)
        return QuestViewHolder(RecyclerQuestItemBinding.bind(view))
    }

    override fun onBindViewHolder(holder: QuestViewHolder, position: Int) {
        holder.bind(questList[position])
    }

    override fun getItemCount(): Int = questList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setList(questList: MutableList<MyQuestResponse>) {
        this.questList = questList
        notifyDataSetChanged()

    }
}