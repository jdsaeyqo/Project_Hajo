package com.ssafy.hajo.board.share

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.hajo.R
import com.ssafy.hajo.databinding.RecyclerShareItemBinding
import com.ssafy.hajo.databinding.RecyclerShareMoreItemBinding
import com.ssafy.hajo.repository.dto.response.ShareResponse

class ShareAdapter(
    var flag: Int,
    val clickListener: (Long) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var shareList = arrayListOf<ShareResponse>()

    inner class ShareViewHolder(val binding: RecyclerShareItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(sharePlan: ShareResponse) {
            binding.apply {
                this.sharePlan = sharePlan

                when (sharePlan.shrplanCategory) {
                    "운동" -> {
                        ivShareCategory.setImageResource(R.drawable.fitness)
                    }
                    "스포츠" -> {
                        ivShareCategory.setImageResource(R.drawable.sports)
                    }
                    "음악" -> {
                        ivShareCategory.setImageResource(R.drawable.guitar)
                    }
                    "미술" -> {
                        ivShareCategory.setImageResource(R.drawable.art)
                    }
                    "학습" -> {
                        ivShareCategory.setImageResource(R.drawable.study)
                    }
                }
            }
        }

        fun click(sharePlan: ShareResponse) {
            binding.clRecyclerShareItem.setOnClickListener {
                clickListener(sharePlan.shrplanSeq)
            }

        }

    }

    inner class ShareMoreViewHolder(val binding: RecyclerShareMoreItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(sharePlan: ShareResponse) {
            binding.apply {
                this.sharePlan = sharePlan

                when (sharePlan.shrplanCategory) {
                    "운동" -> {
                        ivShareCategory.setImageResource(R.drawable.fitness)
                    }
                    "스포츠" -> {
                        ivShareCategory.setImageResource(R.drawable.sports)
                    }
                    "음악" -> {
                        ivShareCategory.setImageResource(R.drawable.guitar)
                    }
                    "미술" -> {
                        ivShareCategory.setImageResource(R.drawable.art)
                    }
                    "학습" -> {
                        ivShareCategory.setImageResource(R.drawable.study)
                    }
                }
            }
        }

        fun click(sharePlan: ShareResponse) {
            binding.clRecyclerShareMoreItem.setOnClickListener {
                clickListener(sharePlan.shrplanSeq)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (flag) {
            0 -> {
                val view =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.recycler_share_item, parent, false)
                ShareViewHolder(RecyclerShareItemBinding.bind(view))
            }
            else -> {
                val view =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.recycler_share_more_item, parent, false)
                ShareMoreViewHolder(RecyclerShareMoreItemBinding.bind(view))
            }

        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        return when (flag) {
            0 -> {
                (holder as ShareViewHolder).bind(shareList[position])
                holder.click(shareList[position])
            }
            else -> {
                (holder as ShareMoreViewHolder).bind(shareList[position])
                holder.click(shareList[position])

            }

        }
    }

    override fun getItemCount(): Int = shareList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setList(shareList: ArrayList<ShareResponse>) {
        this.shareList = shareList
        notifyDataSetChanged()
    }

}