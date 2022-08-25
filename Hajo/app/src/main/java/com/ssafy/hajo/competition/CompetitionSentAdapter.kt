package com.ssafy.hajo.competition

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.hajo.databinding.ListItemRequestSentBinding
import com.ssafy.hajo.entity.Request
import com.ssafy.hajo.registration.UserViewModel
import com.ssafy.hajo.repository.dto.response.UserResponse

class CompetitionSentAdapter(val userInfo: UserResponse): RecyclerView.Adapter<CompetitionSentAdapter.ViewHolder>() {
    private var datas = emptyList<Request>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CompetitionSentAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemRequestSentBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CompetitionSentAdapter.ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int = datas.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(datas: List<Request>) {
        this.datas = datas
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ListItemRequestSentBinding):
        RecyclerView.ViewHolder(binding.root) {
            fun bind(request: Request) {
                binding.apply {
                    this.request = request
                    Log.d("CompetitionSentAdapter", "bind: $userInfo")
                    this.user = userInfo
                }
            }
        }
}