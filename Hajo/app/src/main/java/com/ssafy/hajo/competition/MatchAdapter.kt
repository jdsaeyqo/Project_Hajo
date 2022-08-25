package com.ssafy.hajo.competition

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.hajo.R
import com.ssafy.hajo.databinding.ListItemMatchBinding
import com.ssafy.hajo.entity.Match
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

class MatchAdapter(private val mContext: Context, private val clickListener: (Int) -> Unit):
    RecyclerView.Adapter<MatchAdapter.ViewHolder>() {
    private var datas = emptyList<Match>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val binding = ListItemMatchBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MatchAdapter.ViewHolder, position: Int) {
        holder.bind(datas[position], clickListener)
    }

    override fun getItemCount(): Int  = datas.size

    fun setData(datas: List<Match>) {
        this.datas = datas
    }

    fun getData() = this.datas

    inner class ViewHolder(private val binding: ListItemMatchBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(match: Match, clickListener: (Int) -> Unit) {
            binding.apply {
                this.match = match

                val today = LocalDate.now()
                val sd = LocalDate.parse(match.startDate.substring(0,10), DateTimeFormatter.ISO_DATE)
                val dayDiff = Period.between(sd,today).days
                Log.d("tttt", "sd: $sd")
                Log.d("tttt", "dayDiff: $dayDiff")

                if(match.result != "-") {
                    tvOnProgress.visibility = View.GONE
                    tlDone.visibility = View.GONE
                    cvResult.visibility = View.VISIBLE
                }
                else {
                    for(i in 1..match.duration) {
                        val id = mContext.resources.getIdentifier("iv_day$i","id",mContext.packageName)
                        Log.d("tttt", "bind: $id")
                        val imageView = itemView.findViewById<ImageView>(id)
                        if(i <= dayDiff) {
                            imageView.setImageResource(R.drawable.green_square)
                        }
                        else {
                            imageView.setImageResource(R.drawable.white_square)
                        }
                        imageView.visibility = View.VISIBLE
                    }
                }

                cvResult.setOnClickListener {
                    clickListener(absoluteAdapterPosition)
                }
            }
        }
    }
}