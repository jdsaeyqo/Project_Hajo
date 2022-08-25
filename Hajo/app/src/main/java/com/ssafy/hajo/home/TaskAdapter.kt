package com.ssafy.hajo.home

import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.Task
import com.ssafy.hajo.R
import com.ssafy.hajo.databinding.RecyclerTaskItemBinding
import com.ssafy.hajo.repository.dto.TaskDto

class TaskAdapter(
    val taskList: List<TaskDto>,
    val checkListener: (TaskDto, Boolean, CheckBox) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(private val binding: RecyclerTaskItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(task: TaskDto) {
            binding.apply {
                this.task = task

                when(task.isDone){
                    true -> {
                        cbTask.isChecked = true
                        cbTask.paintFlags = cbTask.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    }
                    false -> {
                        cbTask.isChecked = false
                        cbTask.paintFlags = 0
                    }
                }

                cbTask.setOnCheckedChangeListener { buttonView, isChecked ->
                    checkListener(task, isChecked, binding.cbTask)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_task_item, parent, false)
        return TaskViewHolder(RecyclerTaskItemBinding.bind(view))
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(taskList[position])
    }

    override fun getItemCount(): Int = taskList.size
}