package com.example.materialestimator.adapters

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.materialestimator.R
import com.example.materialestimator.storage.local.entities.Material
import com.example.materialestimator.utilities.Converters


class TaskMaterialsFragmentRVAdapter :
    RecyclerView.Adapter<TaskMaterialsFragmentRVAdapter.MaterialViewHolder?>() {
    private var materials = mutableListOf<Material>()
    private var listener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(id: Long)
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }

    fun setMaterials(materials: List<Material>?) {
        this.materials = materials as MutableList<Material>
        notifyDataSetChanged()
    }

    @NonNull
    override fun onCreateViewHolder(@NonNull viewGroup: ViewGroup, i: Int): MaterialViewHolder {
        val itemView: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.fragment_task_materials_list_item, viewGroup, false)
        return MaterialViewHolder(itemView)
    }

    override fun onBindViewHolder(@NonNull holder: MaterialViewHolder, position: Int) {
        val material = materials[position]
        holder.nameTV.text = material.name
        material.qty?.let { holder.qtyTV.setText(it.toString()) }

    }

    override fun getItemCount() = materials.size

    inner class MaterialViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTV: TextView = itemView.findViewById(R.id.task_material_name_tv)
        val qtyTV: EditText = itemView.findViewById(R.id.task_material_qty_et)

        init {
            itemView.setOnClickListener {
                listener?.onItemClick(
                    materials[absoluteAdapterPosition].materialId
                )
            }

//            qtyTV.addTextChangedListener(object : TextWatcher {
//                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                    if (qtyTV.hasFocus()) {
//                        val material = materials[absoluteAdapterPosition]
//                        material.qty = Converters.stringToInteger(s.toString())
//                    }
//                }
//                override fun beforeTextChanged(
//                    s: CharSequence?,
//                    start: Int,
//                    count: Int,
//                    after: Int
//                ) {
//                }
//                override fun afterTextChanged(s: Editable?) {
//                }
//            })

        }
    }
}