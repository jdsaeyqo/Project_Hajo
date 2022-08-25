package com.ssafy.hajo.settings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.hajo.R
import com.ssafy.hajo.databinding.RecyclerTutorialItemBinding
import com.ssafy.hajo.entity.Tutorial

class TutorialAdapter(val tutorialList: MutableList<Tutorial>) :
    RecyclerView.Adapter<TutorialAdapter.TutorialViewHolder>() {

    inner class TutorialViewHolder(val binding: RecyclerTutorialItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tutorial: Tutorial) {
            binding.ivTutorial.setImageResource(tutorial.img)
            binding.tvTutorial.text = tutorial.text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TutorialViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_tutorial_item, parent, false)
        return TutorialViewHolder(RecyclerTutorialItemBinding.bind(view))
    }

    override fun onBindViewHolder(holder: TutorialViewHolder, position: Int) {
        holder.bind(tutorialList[position])
    }

    override fun getItemCount(): Int = tutorialList.size
}