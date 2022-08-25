package com.ssafy.hajo.board.boast

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssafy.hajo.R
import com.ssafy.hajo.databinding.RecyclerBoardItemBinding
import com.ssafy.hajo.repository.dto.response.BoastHomeResponse

class BoastAdapter(val ctx : Context, val clickListener : (Long) -> Unit) :
    RecyclerView.Adapter<BoastAdapter.BoardViewHolder>() {

    var boastList =  arrayListOf<BoastHomeResponse>()
    inner class BoardViewHolder(val binding: RecyclerBoardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(boast: BoastHomeResponse) {
            binding.boast = boast
            binding.ivBoardImage.clipToOutline = true
            Glide.with(ctx).load(boast.boastImg).centerCrop().into(binding.ivBoardImage)
        }

        fun click(boast: BoastHomeResponse){
            binding.ivBoardImage.setOnClickListener {
                clickListener(boast.boastSeq)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_board_item, parent, false)
        return BoardViewHolder(RecyclerBoardItemBinding.bind(view))
    }

    override fun onBindViewHolder(holder: BoardViewHolder, position: Int) {
        holder.bind(boastList[position])
        holder.click(boastList[position])
    }

    override fun getItemCount(): Int = boastList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setList(boastList : ArrayList<BoastHomeResponse> ){
        this.boastList = boastList
        notifyDataSetChanged()
    }

}