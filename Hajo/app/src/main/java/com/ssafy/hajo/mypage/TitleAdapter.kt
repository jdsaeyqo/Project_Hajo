package com.ssafy.hajo.mypage

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.hajo.databinding.ListItemTitleBinding
import com.ssafy.hajo.entity.Title
import com.ssafy.hajo.repository.dto.response.TitleResponse

class TitleAdapter(private val mContext: Context, private val clickListener:(Int, Int) -> Unit):
    RecyclerView.Adapter<TitleAdapter.ViewHolder>(){
    private var datas = emptyList<TitleResponse>()
    private var using = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TitleAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val binding = ListItemTitleBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TitleAdapter.ViewHolder, position: Int) {
        holder.bind(datas[position])
        holder.itemView.setOnClickListener {
            clickListener(using, position)
            using = position
        }
    }

    override fun getItemCount(): Int = datas.size

    fun setData(datas: List<TitleResponse>) {
        this.datas = datas
    }

    inner class ViewHolder(private val binding: ListItemTitleBinding):
        RecyclerView.ViewHolder(binding.root) {
            fun bind(title: TitleResponse) {
                binding.apply {
                    this.title = title
                    if(title.equipped) {
                        using = absoluteAdapterPosition
                        tvTitleIsUsing.visibility = View.VISIBLE
                    }
                    else {
                        tvTitleIsUsing.visibility = View.GONE
                    }
                }
            }
        }
}