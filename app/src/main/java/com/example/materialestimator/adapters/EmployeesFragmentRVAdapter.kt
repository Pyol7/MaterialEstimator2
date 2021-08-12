package com.example.materialestimator.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.materialestimator.R
import com.example.materialestimator.storage.local.entities.Employee

class EmployeesFragmentRVAdapter: RecyclerView.Adapter<EmployeesFragmentRVAdapter.EmployeeViewHolder?>() {
    private var employees = listOf<Employee>()
    private var listener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemSelected(employee: Employee)
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }

    fun setEmployees(employees: List<Employee>) {
        this.employees = employees
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.fragment_employee_list_item,
            parent,
            false
        )
        return EmployeeViewHolder(v)
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        val context = holder.itemView.context
        val employee = employees[position]
        val imgId = context.resources?.getIdentifier(
            employee.image,
            "drawable",
            context.packageName
        ) as Int
        holder.imageIV.setImageResource(imgId)
        holder.nameTv.text = employee.name
    }

    override fun getItemCount() = employees.size

    inner class EmployeeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)  {
        val imageIV = itemView.findViewById(R.id.employee_image_iv) as ImageView
        val nameTv = itemView.findViewById(R.id.employee_name_tv) as TextView
        val occupationTv =
            itemView.findViewById(R.id.employee_occupation_tv) as TextView
        val teamTv = itemView.findViewById(R.id.employee_team_tv) as TextView

        init {
            itemView.setOnClickListener {
                listener?.onItemSelected(
                    employees[absoluteAdapterPosition]
                )
            }
        }

    }

}
