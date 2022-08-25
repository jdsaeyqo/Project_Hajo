package com.ssafy.hajo.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.hajo.databinding.ListItemTaskBinding

class TaskAdapter(private val datas: MutableList<String>): RecyclerView.Adapter<TaskAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemTaskBinding.inflate(layoutInflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskAdapter.ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int = datas.size

    inner class ViewHolder(private val binding: ListItemTaskBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(str : String) {
            binding.tvTaskName.setText("${absoluteAdapterPosition + 1}. $str")
        }
    }
}