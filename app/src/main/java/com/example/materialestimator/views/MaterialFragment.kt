package com.example.materialestimator.views

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.materialestimator.R
import com.example.materialestimator.TAG
import com.example.materialestimator.storage.local.entities.Material
import com.example.materialestimator.utilities.MoshiConverters
import com.example.materialestimator.utilities.Functions
import com.example.materialestimator.viewModels.MaterialsViewModel
import java.util.*

class MaterialFragment : Fragment(R.layout.fragment_material) {
    private val materialsVM: MaterialsViewModel by viewModels()
    private var properties: ArrayList<Pair<String, String>>? = arrayListOf()
    private var material: Material? = null
    private var materialId = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // If there is a saved id then restore it, else use the one that was passed in.
        materialId = savedInstanceState?.getLong("key") ?: arguments?.getLong("key")!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Views
        val toolbar = view.findViewById(R.id.material_toolbar) as Toolbar
        val rv = view.findViewById(R.id.material_properties_rv) as RecyclerView
        // Toolbar
        toolbar.inflateMenu(R.menu.general_menu)
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        // Adapter
        val rvAdapter = PropertiesListAdapter()
        // RecyclerView
        rv.apply {
            setHasFixedSize(true)
            adapter = rvAdapter
        }

        // Get and observe the Material clicked
        materialsVM.get(materialId).observe(viewLifecycleOwner) { baseMaterial ->
            material = MoshiConverters.convertBaseTypeToSubtype(baseMaterial)
            val imageIv = view.findViewById(R.id.material_image_iv) as ImageView
            val imgId = resources.getIdentifier(
                material?.image,
                "drawable",
                context?.packageName
            )
            imageIv.setImageResource(imgId)
            properties = material?.getOptionalProperties()
            rvAdapter.setData(properties)
        }
    }

    inner class PropertiesListAdapter :
        RecyclerView.Adapter<PropertyViewHolder>() {

        fun setData(data: ArrayList<Pair<String, String>>?) {
            properties = data
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
                : PropertyViewHolder {
            val v = layoutInflater.inflate(
                R.layout.fragment_material_list_item,
                parent,
                false
            )
            return PropertyViewHolder(v)
        }

        override fun onBindViewHolder(holder: PropertyViewHolder, position: Int) {
            val property = properties?.get(position)
            holder.labelTV.text = property?.first
            holder.valueEt.setText(property?.second)
            val editText = holder.valueEt

            editText.setOnFocusChangeListener { v, _ ->
                val view: EditText = v as EditText
                val clickedPropertyName =  // {first = name, second = value}
                    properties?.get(position)?.first.toString().let { output ->
                        output.lowercase().dropLast(1).filter { !it.isWhitespace() }
                    }
                val field = material?.javaClass?.superclass?.getDeclaredField(clickedPropertyName)
                field?.isAccessible = true
                when (field?.type) {
                    Double::class.java -> {
                        editText.inputType =
                            InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
                        if (view.text.toString().isNotEmpty()) {
                            field.setDouble(material, view.text.toString().toDouble())
                        } else {
                            field.setDouble(material, 0.0)
                        }
                    }
                    String::class.java -> {
                        editText.inputType = InputType.TYPE_CLASS_TEXT
                        if (view.text.toString().isNotEmpty()) {
                            field.set(material, view.text.toString())
                        } else {
                            field.set(material, "Please provide a name")
                        }
                    }
                    else -> {
                        Log.i(
                            TAG,
                            "MaterialFragment:setOnFocusChangeListener: Field type not found." +
                                    " Field type : " + field?.type
                        )
                    }
                }
            }

            holder.valueEt.setOnEditorActionListener { v, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    v.clearFocus() // Triggers the last onFocusChanged event to save change
                    context?.let { Functions.hideKeyboard(it, v.rootView) }
                    materialsVM.insert(material)
                }
                false
            }
        }

        override fun getItemCount() = properties!!.size
    }

    inner class PropertyViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        val labelTV = view.findViewById(R.id.material_property_label_tv) as TextView
        val valueEt = view.findViewById(R.id.material_property_value_et) as EditText
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong("key", materialId)
    }

}
