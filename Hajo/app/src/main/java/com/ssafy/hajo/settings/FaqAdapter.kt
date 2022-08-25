package com.ssafy.hajo.settings

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.hajo.R
import com.ssafy.hajo.databinding.RecyclerInfoItemBinding
import com.ssafy.hajo.entity.FAQ

class FaqAdapter(val context: Context, val faqList: MutableList<FAQ>) :
    RecyclerView.Adapter<FaqAdapter.InfoViewHolder>() {


    inner class InfoViewHolder(val binding: RecyclerInfoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(faq: FAQ) {

            binding.tvQ.text = "Q${faq.idx}."
            binding.tvParentQuestion.text = faq.title
            binding.tvChildContent.text = faq.body

            binding.btnInfo.setOnClickListener {
                when (faq.clicked) {
                    false -> {
                        binding.clInfoChild.visibility = View.VISIBLE
                        binding.btnInfo.setImageResource(R.drawable.ic_arrow_up)
                        faq.clicked = true
                    }

                    true -> {
                        binding.clInfoChild.visibility = View.GONE
                        binding.btnInfo.setImageResource(R.drawable.ic_arrow_down)
                        faq.clicked = false
                    }

                }
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_info_item, parent, false)
        return InfoViewHolder(RecyclerInfoItemBinding.bind(view))
    }

    override fun onBindViewHolder(holder: InfoViewHolder, position: Int) {
        holder.bind(faqList[position])
    }

    override fun getItemCount(): Int = faqList.size
}