package com.ssafy.hajo.competition

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.hajo.R
import com.ssafy.hajo.databinding.ListItemRequestFormBinding
import com.ssafy.hajo.repository.dto.response.RequestFormResponse

class RequestFormAdapter(private val mContext: Context): RecyclerView.Adapter<RequestFormAdapter.ViewHolder>() {
    private var datas = emptyList<RequestFormResponse>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RequestFormAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val binding = ListItemRequestFormBinding.inflate(layoutInflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RequestFormAdapter.ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int = datas.size

    fun setData(datas : List<RequestFormResponse>) {
        this.datas = datas
    }

    inner class ViewHolder(private val binding: ListItemRequestFormBinding):
        RecyclerView.ViewHolder(binding.root) {
            fun bind(requestForm: RequestFormResponse) {
                binding.apply {
                    this.requestForm = requestForm

                    val str = "${requestForm.matchUserWinwin}윈윈 ${requestForm.matchUserWin}승 ${requestForm.matchUserDraw}무 ${requestForm.matchUserLose}패"
                    this.tvHistory.setText(str)

//                    val arr_dates = requestForm.grandPlanDates.split(",")
//                    for(i in arr_dates.indices) {
//                        val id = mContext.resources.getIdentifier("iv_square${i+1}","id",mContext.packageName)
//
//                        val imageView = itemView.findViewById<ImageView>(id)
//                        if(arr_dates[i] == "1") {
//                            imageView.setImageResource(R.drawable.green_square)
//                        }
//                    }
//
//                    val arr_history = requestForm.matchHistory.split(",")
//                    for(i in arr_history.indices) {
//                        val id = mContext.resources.getIdentifier("iv_history${i+1}","id",mContext.packageName)
//
//                        val imageView = itemView.findViewById<ImageView>(id)
//                        imageView.visibility = View.VISIBLE
//                        if(arr_history[i] == "1") {
//                            imageView.setImageResource(R.drawable.win)
//                        }
//                        else {
//                            imageView.setImageResource(R.drawable.lose)
//                        }
//                    }
                }
            }
        }
}