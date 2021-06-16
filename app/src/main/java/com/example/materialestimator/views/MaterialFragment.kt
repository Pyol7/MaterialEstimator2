package com.example.materialestimator.views

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.materialestimator.R
import com.example.materialestimator.TAG
import com.example.materialestimator.models.entities.Material
import com.example.materialestimator.utilities.Converters
import com.example.materialestimator.utilities.Functions
import com.example.materialestimator.viewModels.MaterialViewModel
import java.util.*

class MaterialFragment : Fragment() {
    private val vm: MaterialViewModel by activityViewModels()
    private var properties: ArrayList<Pair<String, String>>? = arrayListOf()
    private var material: Material? = null
    private var rvAdapter = PropertiesListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Find and configure the recycler view
        val view = inflater.inflate(R.layout.fragment_material, container, false)
        val rv = view.findViewById(R.id.material_properties_rv) as RecyclerView
        rv.layoutManager = LinearLayoutManager(inflater.context)
        rv.adapter = rvAdapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get and observe the Material clicked
        val id = arguments?.getInt("Key")
        vm.get(id).observe(viewLifecycleOwner, { baseMaterial ->

            material = Converters.convertBaseTypeToSubtype(baseMaterial)

            val imageIv = view.findViewById(R.id.material_image_iv) as ImageView
            val imgId = resources.getIdentifier(
                material?.image,
                "drawable",
                context?.packageName
            )
            imageIv.setImageResource(imgId)

            properties = material?.getOptionalProperties()
            rvAdapter.setData(properties)

        })
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
                    vm.insert(material)
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

}
