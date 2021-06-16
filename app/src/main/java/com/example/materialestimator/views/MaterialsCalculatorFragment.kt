package com.example.materialestimator.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.materialestimator.R
import com.example.materialestimator.utilities.Converters

class MaterialsCalculatorFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Find and configure the recycler view
        return inflater.inflate(R.layout.fragment_materials_calculator, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get the saved Material list
        val json = arguments?.getString("Key")
        val materials = json?.let { Converters.jsonToMaterialList(it) }

        val textView = view.findViewById(R.id.material_calculator_tv) as TextView
        textView.text = materials?.size.toString()
    }
}