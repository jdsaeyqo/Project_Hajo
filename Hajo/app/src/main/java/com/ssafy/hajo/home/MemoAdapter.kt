package com.ssafy.hajo.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.hajo.R
import com.ssafy.hajo.databinding.RecyclerMemoItemBinding
import com.ssafy.hajo.repository.dto.MemoDto

class MemoAdapter(val memoList: MutableList<MemoDto>) :
    RecyclerView.Adapter<MemoAdapter.MemoViewHolder>() {

    inner class MemoViewHolder(private val binding: RecyclerMemoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {

            binding.cbMemo.text = memoList[position].memo
            binding.cbMemo.setOnCheckedChangeListener { buttonView, isChecked ->
                if(isChecked){
                    onRemoveCheckClick.onCheckClick(position,binding.cbMemo)
                }
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_memo_item, parent, false)
        return MemoViewHolder(RecyclerMemoItemBinding.bind(view))
    }

    override fun onBindViewHolder(holder: MemoViewHolder, position: Int) {
        holder.bind(position)

    }

    override fun getItemCount(): Int = memoList.size

    lateinit var onRemoveCheckClick : OnRemoveCheckClick

    interface OnRemoveCheckClick{
        fun onCheckClick(position : Int, cb : CheckBox)
    }

}