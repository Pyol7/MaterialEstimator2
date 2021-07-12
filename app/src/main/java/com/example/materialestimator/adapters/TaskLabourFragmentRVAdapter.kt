package com.example.materialestimator.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.materialestimator.R
import com.example.materialestimator.models.entities.Employee

class TaskLabourFragmentRVAdapter : RecyclerView.Adapter<TaskLabourFragmentRVAdapter.ViewHolder?>() {
    private var employees = arrayListOf<Employee>()

    fun setEmployees(employees: MutableList<Employee>) {
        this.employees = employees as ArrayList<Employee>
        notifyDataSetChanged()
    }

    @NonNull
    override fun onCreateViewHolder(@NonNull viewGroup: ViewGroup, i: Int): TaskLabourFragmentRVAdapter.ViewHolder {
        val itemView: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.fragment_task_labour_list_item, viewGroup, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(@NonNull holder: TaskLabourFragmentRVAdapter.ViewHolder, position: Int) {
        val context = holder.itemView.context
        val employee = employees[position]
        val imgId = context.resources?.getIdentifier(
            employee.image,
            "drawable",
            context.packageName
        ) as Int
        holder.imageTv.setImageResource(imgId)
        holder.nameTv.text = employee.name

    }

    override fun getItemCount() = employees.size

    inner class ViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageTv: ImageView = itemView.findViewById(R.id.labour_image_iv)
        val nameTv: TextView = itemView.findViewById(R.id.labour_name_tv)
    }

}
