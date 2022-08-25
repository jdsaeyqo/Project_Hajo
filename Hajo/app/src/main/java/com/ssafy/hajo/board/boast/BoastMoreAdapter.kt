package com.ssafy.hajo.board.boast

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssafy.hajo.R
import com.ssafy.hajo.databinding.RecyclerBoardMoreItemBinding
import com.ssafy.hajo.repository.dto.response.BoastMoreResponse
import com.ssafy.hajo.util.GlobalApplication
import com.ssafy.hajo.util.ReportPopupMenu
import java.text.SimpleDateFormat
import java.util.*

class BoastMoreAdapter(
    val context: Context,
    val deleteListener: (Long) -> Unit,
    val modifyListener: (BoastMoreResponse) -> Unit,
    val boardReportListener: (Long) -> Unit,
    val boardBlockListener: (Long) -> Unit,
    val userReportListener: (String) -> Unit,
    val userBlockListener: (String) -> Unit,
) :
    RecyclerView.Adapter<BoastMoreAdapter.BoardMoreViewHolder>() {
    var boastMoreList = mutableListOf<BoastMoreResponse>()
    val uid = GlobalApplication.userPrefs.getString("uid", "")

    inner class BoardMoreViewHolder(
        val binding: RecyclerBoardMoreItemBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(boastMore: BoastMoreResponse) {
            binding.apply {
                this.boastMore = boastMore
                Glide.with(context).load(boastMore.wrtImg).fitCenter().into(ivMoreProfileItem)
                Glide.with(context).load(boastMore.boastImg).centerCrop().into(ivMoreImage)

                if (uid == boastMore.wrtUid) {
                    btnDeleteBoard.visibility = View.VISIBLE
                    btnModifyBoard.visibility = View.VISIBLE
                }

                btnDeleteBoard.setOnClickListener {
                    deleteListener(boastMore.boastSeq)
                }

                btnModifyBoard.setOnClickListener {
                    modifyListener(boastMore)
                }
            }


            when (boastMore.userIslike) {
                false -> binding.btnMoreLike.setImageResource(R.drawable.heart_blank)
                true -> binding.btnMoreLike.setImageResource(R.drawable.heart_filled)

            }

            binding.btnMoreLike.setOnClickListener {

                when (boastMore.userIslike) {

                    false -> {
                        boastMore.userIslike = true
                        likeClickListener.clickLike(boastMore.boastSeq, 1)
                        boastMore.boastLikecount++
                        binding.tvMoreLikeCnt.text = "${boastMore.boastLikecount}"
                        binding.btnMoreLike.setImageResource(R.drawable.heart_filled)

                    }
                    true -> {
                        boastMore.userIslike = false
                        likeClickListener.clickLike(boastMore.boastSeq, 0)
                        boastMore.boastLikecount--
                        binding.tvMoreLikeCnt.text = "${boastMore.boastLikecount}"
                        binding.btnMoreLike.setImageResource(R.drawable.heart_blank)
                    }

                }
            }

            val boardReport: (Long) -> (Unit) = {

                if(boastMore.wrtUid != uid){
                    boardReportListener(boastMore.boastSeq)
                }else{
                    boardReportListener(-1L)
                }

            }
            val boardBlock: (Long) -> (Unit) = {
                if(boastMore.wrtUid != uid){
                    boardBlockListener(boastMore.boastSeq)
                }else{
                    boardBlockListener(-1L)
                }
            }
            val userReport: (String) -> (Unit) = {
                if(boastMore.wrtUid != uid){
                    userReportListener(boastMore.wrtUid)
                }else{
                    userReportListener("")
                }

            }
            val userBlock: (String) -> (Unit) = {
                if(boastMore.wrtUid != uid){
                    userBlockListener(boastMore.wrtUid)
                }else{
                    userBlockListener("")
                }

            }

            binding.btnMoreMore.setOnClickListener {
                ReportPopupMenu(context, it, boardReport, boardBlock, userReport, userBlock).showPopup()
            }

        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BoastMoreAdapter.BoardMoreViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_board_more_item, parent, false)
        return BoardMoreViewHolder(RecyclerBoardMoreItemBinding.bind(view))
    }

    override fun onBindViewHolder(holder: BoastMoreAdapter.BoardMoreViewHolder, position: Int) {
        holder.bind(boastMoreList[position])
    }

    override fun getItemCount(): Int = boastMoreList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setList(boastMoreList: MutableList<BoastMoreResponse>) {
        this.boastMoreList = boastMoreList
        notifyDataSetChanged()
    }

    lateinit var likeClickListener: LikeClickListener

    interface LikeClickListener {
        fun clickLike(boastSeq: Long, mode: Int)
    }

}

