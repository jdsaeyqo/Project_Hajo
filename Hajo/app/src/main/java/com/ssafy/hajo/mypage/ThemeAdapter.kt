package com.ssafy.hajo.mypage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.hajo.databinding.ListItemThemeBinding
import com.ssafy.hajo.entity.Theme

class ThemeAdapter(private val mContext: Context, private val clickListener: (Int, Int) -> Unit):
    RecyclerView.Adapter<ThemeAdapter.ViewHolder>() {
    private var datas = emptyList<Theme>()
    private var using = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThemeAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val binding = ListItemThemeBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ThemeAdapter.ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int = datas.size

    fun setData(datas: List<Theme>) {
        this.datas = datas
    }

    inner class ViewHolder(private val binding: ListItemThemeBinding):
        RecyclerView.ViewHolder(binding.root) {
            fun bind(theme: Theme) {
                binding.apply {
                    this.theme = theme
                    if(!theme.using) {
                        tvThemeIsUsing.visibility = View.INVISIBLE
                        btnThemeSelect.visibility = View.VISIBLE
                    }
                    else {
                        using = absoluteAdapterPosition
                        btnThemeSelect.visibility = View.INVISIBLE
                        tvThemeIsUsing.visibility = View.VISIBLE
                    }

                    btnThemeSelect.setOnClickListener {
                        clickListener(using, absoluteAdapterPosition)
                        using = absoluteAdapterPosition
                    }
                }
            }
        }
}