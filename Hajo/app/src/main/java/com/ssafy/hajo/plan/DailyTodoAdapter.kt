package com.ssafy.hajo.plan

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.hajo.R
import com.ssafy.hajo.home.TodoAdapter
import org.w3c.dom.Text

class DailyTodoAdapter(val context: Context): RecyclerView.Adapter<DailyTodoAdapter.TodoViewHolder>() {

    var list = mutableListOf<Task>()

    inner class TodoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        var title = itemView.findViewById<TextView>(R.id.tv_task_content)
        var edit = itemView.findViewById<TextView>(R.id.tv_task_edit)
        var checkbox = itemView.findViewById<CheckBox>(R.id.checkbox)

        var memoImg = itemView.findViewById<ImageView>(R.id.img_memo)
        var pictureImg = itemView.findViewById<ImageView>(R.id.img_picture)
        var alramImg = itemView.findViewById<ImageView>(R.id.img_alram)

        @SuppressLint("ResourceAsColor")
        fun onBind(data: Task) {
            title.text = data.title
            checkbox.isChecked = data.isDone
            memoImg.visibility = if(data.memo == "") View.GONE else View.VISIBLE
            alramImg.visibility = if(data.alramtime == "알람 설정") View.GONE else View.GONE
            pictureImg.visibility  = if(data.img == "") View.GONE else View.VISIBLE


            if (data.isDone) { // 이미 완료 했으면
                title.paintFlags = title.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
//                title.setTextColor(R.color.grey_50)
            } else {
                title.paintFlags = title.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }

            checkbox.setOnClickListener{
                itemCheckBoxClickListener.onClick(it, layoutPosition, list[layoutPosition].taskSeq.toLong(),list[layoutPosition].isDone)
            }

            title.setOnClickListener{
                detailClickListener.onClick(it, layoutPosition, list[layoutPosition].taskSeq.toLong(), list[layoutPosition].isDone)
            }

            edit.setOnClickListener {
                detailClickListener.onClick(it, layoutPosition, list[layoutPosition].taskSeq.toLong(), list[layoutPosition].isDone)
            }

            memoImg.setOnClickListener{
                detailClickListener.onClick(it, layoutPosition, list[layoutPosition].taskSeq.toLong(), list[layoutPosition].isDone)
            }

            pictureImg.setOnClickListener{
                detailClickListener.onClick(it, layoutPosition, list[layoutPosition].taskSeq.toLong(), list[layoutPosition].isDone)
            }



        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.daily_tasks_item_view, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun update(newList: MutableList<Task>) {
        this.list = newList
        notifyDataSetChanged()
    }

    fun updateTaskChecked(position: Int) {
        Log.d("error1","updateTaskChecked position $position")
        Log.d("error1","updateTaskChecked list.size ${list.size}")
        if(list.size == 0) {
            notifyDataSetChanged()
            return
        } else if(position < 0) {
            return
        }
        this.list[position].isDone = true
        notifyDataSetChanged()
    }

    interface ItemCheckBoxClickListener {
        fun onClick(view: View, position: Int, taskSqe: Long, isDone: Boolean)
    }

    interface DetailClickListener {
        fun onClick(view: View, position: Int, taskSqe: Long, isDone: Boolean)
    }

    private lateinit var itemCheckBoxClickListener: ItemCheckBoxClickListener
    private lateinit var detailClickListener: DetailClickListener

    fun setItemCheckBoxClickListener(itemCheckBoxClickListener: ItemCheckBoxClickListener) {
        this.itemCheckBoxClickListener = itemCheckBoxClickListener
    }

    fun setDetailClickListener(detailClickListener: DetailClickListener) {
        this.detailClickListener = detailClickListener
    }
}