package com.example.materialestimator.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.materialestimator.R
import com.example.materialestimator.TAG
import com.example.materialestimator.models.entities.Task


class TasksFragmentAdapter : RecyclerView.Adapter<TasksFragmentAdapter.ViewHolder?>() {
    private var tasks = arrayListOf<Task>()
    private var listener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(taskID: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    fun setTasks(tasks: List<Task>) {
        this.tasks = tasks as ArrayList<Task>
        notifyDataSetChanged()
    }

    @NonNull
    override fun onCreateViewHolder(@NonNull viewGroup: ViewGroup, i: Int): ViewHolder {
        val itemView: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.task_list_item, viewGroup, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(@NonNull holder: ViewHolder, position: Int) {
        val task = tasks[position]
        holder.titleTv.text = task.title
        val formattedText: String = String.format(
            holder.itemView.resources.getString(R.string.append_percent),
            task.percentCompleted,
        )
        holder.percentCompletedTv.text = formattedText
    }

    override fun getItemCount() = tasks.size

    inner class ViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTv: TextView = itemView.findViewById(R.id.task_title_tv)
        val percentCompletedTv: TextView = itemView.findViewById(R.id.task_percent_completed_tv)

        init {
            itemView.setOnClickListener {
                // When a task is selected, send the task ID to the listeners
                listener?.onItemClick(
                    tasks[absoluteAdapterPosition].ID
                )
            }
        }
    }
}

