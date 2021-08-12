package com.example.materialestimator.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.materialestimator.R
import com.example.materialestimator.storage.local.entities.Employee
import com.example.materialestimator.storage.local.relationships.TaskWithEmployees

class TaskEmployeesFragmentRVAdapter : RecyclerView.Adapter<TaskEmployeesFragmentRVAdapter.ViewHolder?>() {
    private var employees = arrayListOf<Employee>()
    private var listener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(id: Long)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    fun setEmployees(employees: List<Employee>) {
        this.employees = employees as ArrayList<Employee>
        notifyDataSetChanged()
    }

    @NonNull
    override fun onCreateViewHolder(@NonNull viewGroup: ViewGroup, i: Int): TaskEmployeesFragmentRVAdapter.ViewHolder {
        val itemView: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.fragment_employee_list_item, viewGroup, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(@NonNull holder: TaskEmployeesFragmentRVAdapter.ViewHolder, position: Int) {
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
        val imageTv = itemView.findViewById(R.id.employee_image_iv) as ImageView
        val nameTv = itemView.findViewById(R.id.employee_name_tv) as TextView
        init {
            itemView.setOnClickListener {
                listener?.onItemClick(
                    employees[absoluteAdapterPosition].employeeId
                )
            }
        }
    }

}
