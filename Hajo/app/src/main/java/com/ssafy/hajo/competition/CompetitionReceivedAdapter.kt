package com.ssafy.hajo.competition

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.hajo.databinding.ListItemRequestReceivedBinding
import com.ssafy.hajo.entity.Request

class CompetitionReceivedAdapter(private val clickListener: (Int, Boolean) -> Unit):
    RecyclerView.Adapter<CompetitionReceivedAdapter.ViewHolder>() {
    private var datas = emptyList<Request>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CompetitionReceivedAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemRequestReceivedBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CompetitionReceivedAdapter.ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int = datas.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(datas: List<Request>) {
        this.datas = datas
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ListItemRequestReceivedBinding):
        RecyclerView.ViewHolder(binding.root) {
            fun bind(request: Request) {
                binding.apply {
                    this.request = request

                    ivAccept.setOnClickListener {
                        clickListener(absoluteAdapterPosition, true)
                    }

                    ivDecline.setOnClickListener {
                        clickListener(absoluteAdapterPosition, false)
                    }
                }
            }
        }
}