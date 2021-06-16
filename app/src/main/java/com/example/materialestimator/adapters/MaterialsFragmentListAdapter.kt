package com.example.materialestimator.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.materialestimator.R
import com.example.materialestimator.TAG
import com.example.materialestimator.models.entities.Material
import com.example.materialestimator.utilities.Converters

class MaterialsFragmentListAdapter() :
    RecyclerView.Adapter<MaterialsFragmentListAdapter.MaterialViewHolder>() {

    private lateinit var mContext: Context
    private var materials = arrayListOf<Material>()
    private var listener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemSelected(json: String)
        fun onItemClicked(ID: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }

    fun setList(newMaterials: List<Material>) {
        val diffCallback = MaterialDiffCallback(materials, newMaterials)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        materials.clear()
        materials.addAll(newMaterials)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MaterialViewHolder {
        mContext = parent.context
        val inflater = LayoutInflater.from(mContext)
        val v = inflater.inflate(
            R.layout.fragment_materials_list_item,
            parent,
            false
        )
        return MaterialViewHolder(v)
    }

    override fun onBindViewHolder(
        holder: MaterialViewHolder,
        position: Int
    ) {
        val material = materials[position]
        val imgId = mContext.resources?.getIdentifier(
            material.image,
            "drawable",
            mContext.packageName
        ) as Int
        holder.imageIV.setImageResource(imgId)
        holder.nameTv.text = material.name
        holder.unitPriceTv.text = material.unitprice.toString()
        holder.categoryTv.text = material.selected.toString()
        holder.selectedCb.isChecked = material.selected

    }

    override fun getItemCount() = materials.size

    inner class MaterialViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val listItemView = itemView.findViewById(R.id.material_list_cl) as View
        val imageIV = itemView.findViewById(R.id.material_list_image_iv) as ImageView
        val nameTv = itemView.findViewById(R.id.material_list_name_tv) as TextView
        val unitPriceTv =
            itemView.findViewById(R.id.material_list_unit_price_tv) as TextView
        val categoryTv = itemView.findViewById(R.id.material_list_category_tv) as TextView
        val selectedCb = itemView.findViewById(R.id.material_list_selected_cb) as CheckBox

        init {
            /**
             * Send the selected material to the listener.
             * OnClickListener was used instead of onCheckedListener as the later
             * produces multiple calls.
             */
            selectedCb.setOnClickListener {
                val checkBox = it as CheckBox
                val m = materials[absoluteAdapterPosition]
                m.selected = checkBox.isChecked
                val json = Converters.materialToJson(m)
                listener?.onItemSelected(json)
            }
            /**
             * Send the Material ID to the listener.
             */
            listItemView.setOnClickListener {
                listener?.onItemClicked(materials[absoluteAdapterPosition].materialID)
            }
        }

    }
}

/**
 * Provides fast and smooth list changes
 */
class MaterialDiffCallback(
    private val oldList: List<Material>, private val newList:
    List<Material>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val itemsAreSame =
            oldList[oldItemPosition].materialID == newList[newItemPosition].materialID

//            Log.i(TAG, "areItemsTheSame = $itemsAreSame")

        return itemsAreSame
    }

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        val (_, _, name, unitprice, length, width, coverage, image, materialCategoryID, selected) = oldList[oldPosition]
        val (_, _, name1, unitprice1, length1, width1, coverage1, image1, materialCategoryID1, selected1) = newList[newPosition]

        val contentIsSame =
            name == name1 &&
                    unitprice == unitprice1 &&
                    length == length1 &&
                    width == width1 &&
                    coverage == coverage1 &&
                    image == image1 &&
                    materialCategoryID == materialCategoryID1 &&
                    selected == selected1

//            Log.i(TAG, "areContentsTheSame = $contentIsSame")

        return contentIsSame
    }

    @Nullable
    override fun getChangePayload(oldPosition: Int, newPosition: Int): Any? {
        return super.getChangePayload(oldPosition, newPosition)
    }
}
