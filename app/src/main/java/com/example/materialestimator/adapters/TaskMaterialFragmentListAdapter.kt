package com.example.materialestimator.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.materialestimator.R
import com.example.materialestimator.models.entities.Material

class TaskMaterialFragmentListAdapter : RecyclerView.Adapter<TaskMaterialFragmentListAdapter.MaterialViewHolder?>() {
    private var materials = arrayListOf<Material>()
    private var listener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(materialID: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }

    fun setMaterials(materials: List<Material>?) {
        this.materials = materials as ArrayList<Material>
        notifyDataSetChanged()
    }

    @NonNull
    override fun onCreateViewHolder(@NonNull viewGroup: ViewGroup, i: Int): MaterialViewHolder {
        val itemView: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.task_material_list_item, viewGroup, false)
        return MaterialViewHolder(itemView)
    }

    override fun onBindViewHolder(@NonNull holder: MaterialViewHolder, position: Int) {
//        val context = holder.itemView.context
        val material = materials[position]
//        val imgId = context.resources?.getIdentifier(
//            material.image,
//            "drawable",
//            context.packageName
//        ) as Int
//        holder.imageTV.setImageResource(imgId)
        holder.nameTV.text = material.name
//        holder.startDateTV.text = project.startDate.toString()
//        holder.clientTV.text = project.clientContactID.toString()

    }

    override fun getItemCount() = materials.size

    inner class MaterialViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTV: TextView = itemView.findViewById(R.id.task_material_name_tv)
//        val imageTV: ImageView = itemView.findViewById(R.id.project_image_iv)
//        val startDateTV: TextView = itemView.findViewById(R.id.project_start_tv)
//        val clientTV: TextView = itemView.findViewById(R.id.project_client_tv)

        init {
            itemView.setOnClickListener {
                listener?.onItemClick(
                    materials[absoluteAdapterPosition].id
                )
            }
        }
    }
}