package com.example.materialestimator.views

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.materialestimator.R
import com.example.materialestimator.adapters.MaterialsCategoriesSpinnerAdapter
import com.example.materialestimator.adapters.MaterialsFragmentRVAdapter
import com.example.materialestimator.storage.local.entities.Material
import com.example.materialestimator.utilities.MoshiConverters
import com.example.materialestimator.viewModels.MaterialsViewModel
import com.example.materialestimator.viewModels.SharedViewModel
import com.example.materialestimator.viewModels.TasksViewModel

class MaterialsFragment : Fragment(R.layout.fragment_materials),
    MaterialsFragmentRVAdapter.OnItemClickListener {
    private val materialsVm: MaterialsViewModel by viewModels()
    private lateinit var rvAdapter: MaterialsFragmentRVAdapter
    private lateinit var counterTv: TextView
    private lateinit var addBtn: Button
    private var selectedMaterials = mutableListOf<Material>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // View
        counterTv = view.findViewById(R.id.materials_counter_tv)
        addBtn = view.findViewById(R.id.materials_add_btn)
        val rv = view.findViewById(R.id.materials_rv) as RecyclerView
        val spinner = view.findViewById(R.id.materials_spinner) as Spinner

        // Setup the recyclerview
        rv.apply {
            setHasFixedSize(true)
            rvAdapter = MaterialsFragmentRVAdapter()
            rvAdapter.setOnItemClickListener(this@MaterialsFragment)
            adapter = rvAdapter
            adapter?.stateRestorationPolicy =
                RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        }
        // Set up filters
        val spinnerAdapter = MaterialsCategoriesSpinnerAdapter(requireContext())
        spinner.adapter = spinnerAdapter

        materialsVm.getAllCategories().observe(viewLifecycleOwner) { categories ->
            spinnerAdapter.setList(categories)
            materialsVm.selectedCategoryID.observe(viewLifecycleOwner) { id ->
                spinner.setSelection(id.minus(1).toInt())
            }
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    materialsVm.selectedCategoryID.value = categories[position].materialCategoryId
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }

        //Display filtered materials
        materialsVm.materialsByCategoryId.observe(viewLifecycleOwner) {
            rvAdapter.setList(it)
        }

        addBtn.setOnClickListener {
            val json = MoshiConverters.materialsToJson(selectedMaterials)
            /**
             * Sends a bundle to any fragment that implements
             * setFragmentResultListener("materialsFragmentRequestKey")
             * In this case TaskFragment.
             */
            setFragmentResult(
                "materialsFragmentRequestKey",
                bundleOf("key" to json)
            )

            findNavController().navigateUp()
        }
    }

    /**
     * Receives the selected material from MaterialsFragmentListAdapter(), adds it to
     * property selectedMaterials and update the counter.
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
    }

    /**
     * Receives the selected material id from MaterialsFragmentRVAdapter() and navigates to
     * that material.
     */
    override fun onItemClicked(id: Long) {
        arguments = bundleOf("key" to id)
        view?.findNavController()
            ?.navigate(R.id.action_materialsFragment_to_materialFragment, arguments)
    }

}


