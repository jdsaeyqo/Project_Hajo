package com.ssafy.hajo.competition

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.hajo.R
import com.ssafy.hajo.databinding.ListItemHistoryBinding
import com.ssafy.hajo.repository.dto.response.HistoryResponse

class HistoryAdapter(private val mContext: Context, private var datas: MutableList<HistoryResponse>)
    : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val binding = ListItemHistoryBinding.inflate(layoutInflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryAdapter.ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int = datas.size

    fun setData(datas: MutableList<HistoryResponse>) {
        this.datas = datas
    }

    inner class ViewHolder(private val binding: ListItemHistoryBinding):
        RecyclerView.ViewHolder(binding.root){
            fun bind(historyResponse: HistoryResponse) {
                binding.apply {
                    this.history = historyResponse

                    if(historyResponse.matchResult == "L") {
                        ivResult.setImageResource(R.drawable.lose)
                        tvPoint.setText("-50p")
                        tvPoint.setTextColor(ContextCompat.getColor(mContext, R.color.error))
                        cvHistory.setBackgroundResource(R.drawable.history_lose_background)
                    }
                    else {
                        ivResult.setImageResource(R.drawable.win)
                        tvPoint.setText("+100p")
                        tvPoint.setTextColor(ContextCompat.getColor(mContext, R.color.done))
                        cvHistory.setBackgroundResource(R.drawable.history_win_background)
                    }
                }
            }
    }
}