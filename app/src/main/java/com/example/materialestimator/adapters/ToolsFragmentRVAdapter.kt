package com.example.materialestimator.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.materialestimator.R
import com.example.materialestimator.storage.local.entities.Tool

class ToolsFragmentRVAdapter: RecyclerView.Adapter<ToolsFragmentRVAdapter.ToolViewHolder?>() {
    private var tools = listOf<Tool>()
    private var listener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemSelected(id: Long)
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }

    fun setTools(tools: List<Tool>) {
        this.tools = tools
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToolViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.fragment_tools_list_item,
            parent,
            false
        )
        return ToolViewHolder(v)
    }

    override fun onBindViewHolder(holder: ToolViewHolder, position: Int) {
        val context = holder.itemView.context
        val tool = tools[position]
        val imgId = context.resources?.getIdentifier(
            tool.image,
            "drawable",
            context.packageName
        ) as Int
        holder.imageIV.setImageResource(imgId)
        holder.typeTv.text = tool.type
        holder.makeTv.text = tool.make
    }

    override fun getItemCount() = tools.size

    inner class ToolViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)  {
        val imageIV = itemView.findViewById(R.id.tool_image_iv) as ImageView
        val typeTv = itemView.findViewById(R.id.tool_type_tv) as TextView
        val makeTv = itemView.findViewById(R.id.tool_make_tv) as TextView

        init {
            itemView.setOnClickListener {
                listener?.onItemSelected(
                    tools[absoluteAdapterPosition].toolId
                )
            }
        }

    }

}
