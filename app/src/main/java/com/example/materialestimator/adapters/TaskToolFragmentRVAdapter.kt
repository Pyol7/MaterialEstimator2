package com.example.materialestimator.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.materialestimator.R
import com.example.materialestimator.models.entities.Tool
import com.example.materialestimator.utilities.MoshiConverters

class TaskToolFragmentRVAdapter : RecyclerView.Adapter<TaskToolFragmentRVAdapter.ViewHolder?>() {
    private var tools = arrayListOf<Tool>()
    private var listener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(jsonTool: String)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    fun setTools(tools: MutableList<Tool>) {
        this.tools = tools as ArrayList<Tool>
        notifyDataSetChanged()
    }

    @NonNull
    override fun onCreateViewHolder(@NonNull viewGroup: ViewGroup, i: Int): TaskToolFragmentRVAdapter.ViewHolder {
        val itemView: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.fragment_task_tools_list_item, viewGroup, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(@NonNull holder: TaskToolFragmentRVAdapter.ViewHolder, position: Int) {
        val context = holder.itemView.context
        val tool = tools[position]
        val imgId = context.resources?.getIdentifier(
            tool.image,
            "drawable",
            context.packageName
        ) as Int
        holder.imageTv.setImageResource(imgId)
        holder.typeTv.text = tool.type
        holder.makeTv.text = tool.make

    }

    override fun getItemCount() = tools.size

    inner class ViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageTv: ImageView = itemView.findViewById(R.id.tool_image_iv)
        val typeTv: TextView = itemView.findViewById(R.id.tool_type_tv)
        val makeTv: TextView = itemView.findViewById(R.id.tool_make_tv)

        init {
            itemView.setOnClickListener {
                listener?.onItemClick(
                    MoshiConverters.toolToJson(tools[absoluteAdapterPosition])
                )
            }
        }
    }

}
