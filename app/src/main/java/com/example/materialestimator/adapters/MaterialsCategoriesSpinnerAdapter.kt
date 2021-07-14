package com.example.materialestimator.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.materialestimator.R
import com.example.materialestimator.models.entities.MaterialCategory

class MaterialsCategoriesSpinnerAdapter(context: Context) :
    ArrayAdapter<MaterialCategory>(context, 0) {

    fun setList(list: List<MaterialCategory>) {
        clear()
        addAll(list)
        notifyDataSetChanged()
    }

    private fun createView(position: Int, convertView: View?, parent: ViewGroup): View {
        val category = getItem(position)
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.fragment_materials_categories_list_item, parent, false)
        val nameView = view?.findViewById(R.id.materials_category_name_tv) as TextView
        nameView.text = category?.name
        return view
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createView(position, convertView, parent)
    }

}