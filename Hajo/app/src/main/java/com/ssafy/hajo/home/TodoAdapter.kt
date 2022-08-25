package com.ssafy.hajo.home

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.hajo.R
import com.ssafy.hajo.databinding.RecyclerHomeTodoItemBinding
import com.ssafy.hajo.repository.dto.TaskDto
import com.ssafy.hajo.repository.dto.response.HomeResponse


class TodoAdapter(

    val checkListener: (TaskDto, Boolean, CheckBox) -> Unit,
    val clickListener : (Long) -> Unit,
    val addTaskListener : (Long) -> Unit

) :
    RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {
    var todoList =  mutableListOf<HomeResponse>()
    lateinit var task: TaskDto

    inner class TodoViewHolder(
        private val binding: RecyclerHomeTodoItemBinding,
        val context: Context
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(plan: HomeResponse) {
           binding.apply {
               this.planHome = plan
               Log.d("TodoAdapter_", "bind: ${plan.isMatch}")
               if(plan.isMatch){
                   Log.d("TodoAdapter_", "bind: ${plan.isMatch}")
                   btnAddTask.visibility = View.GONE
               }

               btnAddTask.setOnClickListener {
                   addTaskListener(plan.smallplanSeq)
               }

               tvHomeTodoLargePlan.setOnClickListener {
                   clickListener(plan.grandplanSeq)
               }

           }

            val taskAdapter =
                TaskAdapter(plan.subDto) { taskDto: TaskDto, b: Boolean, cb: CheckBox ->
                    task = taskDto

                    checkListener(task, b, cb)
                }

            binding.rvTask.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            binding.rvTask.adapter = taskAdapter
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_home_todo_item, parent, false)
        return TodoViewHolder(RecyclerHomeTodoItemBinding.bind(view), parent.context)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(todoList[position])
    }

    override fun getItemCount(): Int = todoList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setList(todoList : MutableList<HomeResponse>){
        this.todoList = todoList
        notifyDataSetChanged()
    }

}