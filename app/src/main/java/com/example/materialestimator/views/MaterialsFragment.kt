package com.example.materialestimator.views

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.materialestimator.R
import com.example.materialestimator.adapters.MaterialsCategoriesSpinnerAdapter
import com.example.materialestimator.adapters.MaterialsFragmentListAdapter
import com.example.materialestimator.models.entities.Material
import com.example.materialestimator.utilities.MoshiConverters
import com.example.materialestimator.viewModels.MaterialsViewModel
import com.example.materialestimator.viewModels.TaskViewModel

class MaterialsFragment : Fragment(R.layout.fragment_materials),
    MaterialsFragmentListAdapter.OnItemClickListener {
    private val materialsVm: MaterialsViewModel by viewModels()
    private val taskVm: TaskViewModel by activityViewModels()
    private lateinit var rvAdapter: MaterialsFragmentListAdapter
    private lateinit var counterTv: TextView
    private lateinit var saveBtn: Button
    private var selectedMaterials = mutableListOf<Material>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Get counter view
        counterTv = view.findViewById(R.id.materials_counter_tv)
        saveBtn = view.findViewById(R.id.materials_add_btn)
        // Setup the recyclerview
        val rv = view.findViewById(R.id.materials_rv) as RecyclerView
        rv.apply {
            setHasFixedSize(true)
            rvAdapter = MaterialsFragmentListAdapter()
            rvAdapter.setOnItemClickListener(this@MaterialsFragment)
            adapter = rvAdapter
            adapter?.stateRestorationPolicy =
                RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        }
        // Set up filters
        val spinner = view.findViewById(R.id.materials_spinner) as Spinner
        val spinnerAdapter = MaterialsCategoriesSpinnerAdapter(requireContext())
        spinner.adapter = spinnerAdapter

        materialsVm.getAllCategories().observe(viewLifecycleOwner) { categories ->
            spinnerAdapter.setList(categories)
            materialsVm.selectedCategoryID.observe(viewLifecycleOwner) { id ->
                spinner.setSelection(id.minus(1))
            }
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    materialsVm.selectedCategoryID.value = categories[position].id
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }

        //Display filtered materials
        materialsVm.materialsByCategoryId.observe(viewLifecycleOwner) {
            rvAdapter.setList(it)
        }

        saveBtn.setOnClickListener {
            taskVm.addMaterialsToSelectedTaskMaterials(selectedMaterials)
            findNavController().navigateUp()
        }
    }

    /**
     * Receives the selected material from MaterialsFragmentListAdapter() and update its record
     * in the db.
     */
    override fun onItemSelected(json: String) {
        val material = MoshiConverters.jsonToMaterial(json)
        if (material?.selected == true) {
            selectedMaterials.add(material)
            counterTv.text = selectedMaterials.size.toString()
        } else {
            selectedMaterials.remove(material)
            counterTv.text = selectedMaterials.size.toString()
        }
//        materialsVm.update(material)
    }

    /**
     * Receives the selected material ID from MaterialsFragmentListAdapter() and navigates to
     * that material.
     */
    override fun onItemClicked(ID: Int) {
        val bundle = bundleOf("Key" to ID)
        view?.findNavController()
            ?.navigate(R.id.action_materialsFragment_to_materialFragment, bundle)
    }


}


