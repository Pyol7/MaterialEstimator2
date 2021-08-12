package com.example.materialestimator.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.materialestimator.R
import com.example.materialestimator.storage.local.relationships.ProjectWithTasks

class ProjectsFragmentRVAdapter : RecyclerView.Adapter<ProjectsFragmentRVAdapter.ProjectViewHolder?>() {
    private var projectsWithTasks = listOf<ProjectWithTasks>()
    private var listener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(projectId: Long?)
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }

    fun setProjects(projectsWithTasks: List<ProjectWithTasks>) {
        this.projectsWithTasks = projectsWithTasks
        notifyDataSetChanged()
    }

    @NonNull
    override fun onCreateViewHolder(@NonNull viewGroup: ViewGroup, i: Int): ProjectViewHolder {
        val itemView: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.fragment_projects_list_item, viewGroup, false)
        return ProjectViewHolder(itemView)
    }

    override fun onBindViewHolder(@NonNull holder: ProjectViewHolder, position: Int) {
        val context = holder.itemView.context
        val projectsWithTasks = projectsWithTasks[position]
        val imgId = context.resources?.getIdentifier(
            projectsWithTasks.project?.image,
            "drawable",
            context.packageName
        ) as Int
        holder.imageTV.setImageResource(imgId)
        holder.nameTV.text = projectsWithTasks.project?.name
//        holder.startDateTV.text = project.startDate.toString()
//        holder.clientTV.text = project.clientContactID.toString()

    }

    override fun getItemCount() = projectsWithTasks.size

    inner class ProjectViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTV: TextView = itemView.findViewById(R.id.project_name_tv)
        val imageTV: ImageView = itemView.findViewById(R.id.project_image_iv)
//        val startDateTV: TextView = itemView.findViewById(R.id.project_start_tv)
//        val clientTV: TextView = itemView.findViewById(R.id.project_client_tv)

        init {
            itemView.setOnClickListener {
                listener?.onItemClick(
                    projectsWithTasks[absoluteAdapterPosition].project?.projectId
                )
            }
        }
    }
}