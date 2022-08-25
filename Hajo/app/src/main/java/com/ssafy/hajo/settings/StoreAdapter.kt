package com.ssafy.hajo.settings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.hajo.R
import com.ssafy.hajo.databinding.RecyclerStoreItemBinding
import com.ssafy.hajo.entity.Store
import com.ssafy.hajo.util.DiarySkin

class StoreAdapter(val clickListener : (Store) -> Unit) : RecyclerView.Adapter<StoreAdapter.StoreViewHolder>() {

    var storeList = mutableListOf<Store>()

    private val diaryList = DiarySkin.diaryList
    inner class StoreViewHolder(val binding: RecyclerStoreItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(store: Store) {
            binding.ivStoreItem.setImageResource(diaryList[store.diaryType.toInt()])
            binding.tvDiaryTitle.text = store.diaryTitle
            binding.tvMoney.text = "${store.price}P"

            binding.btnPurchase.setOnClickListener {
                clickListener(store)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_store_item, parent, false)
        return StoreViewHolder(RecyclerStoreItemBinding.bind(view))
    }

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        holder.bind(storeList[position])
    }

    override fun getItemCount(): Int = storeList.size
}