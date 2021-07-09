package com.example.materialestimator.views

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.materialestimator.R
import com.example.materialestimator.TAG
import com.example.materialestimator.adapters.MaterialsFragmentListAdapter
import com.example.materialestimator.models.entities.Category
import com.example.materialestimator.utilities.MoshiConverters
import com.example.materialestimator.viewModels.MaterialsViewModel

class MaterialsFragment : Fragment(R.layout.fragment_materials),
    MaterialsFragmentListAdapter.OnItemClickListener {
//    private val actionModeCallback: ActionMode.Callback = ActionModeCallback()
    private val vm: MaterialsViewModel by viewModels()
    private var rvAdapter: MaterialsFragmentListAdapter? = null
    private var actionMode: ActionMode? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Setup the toolbar
        val toolbar = view.findViewById(R.id.materials_toolbar) as Toolbar
        toolbar.inflateMenu(R.menu.general_toolbar_menu)
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        // Setup the recyclerview
        val rv = view.findViewById(R.id.materials_rv) as RecyclerView
        rv.apply {
            setHasFixedSize(true)
            rvAdapter = MaterialsFragmentListAdapter()
            rvAdapter!!.setOnItemClickListener(this@MaterialsFragment)
            adapter = rvAdapter
            adapter?.stateRestorationPolicy =
                RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        }

        // Set up filters
        val spinner = view.findViewById(R.id.spinner) as Spinner
        val spinnerAdapter = CategoriesAdapter(requireContext())
        spinner.adapter = spinnerAdapter

        vm.getAllCategories().observe(viewLifecycleOwner) { categories ->
            spinnerAdapter.setList(categories)
            vm.selectedCategoryID.observe(viewLifecycleOwner) { id ->
                spinner.setSelection(id.minus(1))
            }
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    vm.selectedCategoryID.value = categories[position].id
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }

        //Display filtered materials
        vm.materialsByCategoryId.observe(viewLifecycleOwner) {
            rvAdapter?.setList(it)
        }

        //Show or hide the context bar based on observing the selected materials
        vm.selectedMaterials.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
//                if (actionMode == null) {
//                    actionMode = activity?.startActionMode(actionModeCallback)
//                }
                toolbar.title = "${it.size} selected"
            } else {
                toolbar.title = "Select materials"
            }
        }

    }

    /**
     * Receives the selected material from MaterialsFragmentListAdapter() and update its record
     * in the db.
     */
    override fun onItemSelected(json: String) {
        val material = MoshiConverters.jsonToMaterial(json)
        vm.update(material)
    }

    /**
     * Receives the selected material ID from MaterialsFragmentListAdapter() and navigates to
     * that material.
     */
    override fun onItemClicked(ID: Int) {
//        actionMode?.finish()
        val bundle = bundleOf("Key" to ID)
        view?.findNavController()
            ?.navigate(R.id.action_materialsFragment_to_materialFragment, bundle)
    }

    /**
     * The custom list adapter used by the spinner
     */
    class CategoriesAdapter(context: Context) :
        ArrayAdapter<Category>(context, 0) {

        fun setList(list: List<Category>) {
            clear()
            addAll(list)
            notifyDataSetChanged()
        }

        private fun createView(position: Int, convertView: View?, parent: ViewGroup): View {
            val category = getItem(position)
            val view = convertView ?: LayoutInflater.from(context)
                .inflate(R.layout.fragment_materials_categories_list_item, parent, false)
            val idView = view.findViewById(R.id.materials_category_id_tv) as TextView
            idView.text = category?.id.toString()
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

    /**
     * Controls the context action bar
     */
    private inner class ActionModeCallback : ActionMode.Callback {

        override fun onCreateActionMode(mode: ActionMode, menu: Menu?): Boolean {
            mode.menuInflater.inflate(R.menu.contect_menu, menu)
            return true
        }

        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {

            return when (item.itemId) {
                R.id.action_save -> {
                    mode.finish() // Calls onDestroyActionMode()
                    // Notify user of saved list

                    true
                }
                else -> false
            }
        }

        /**
         * Internally called when context menu up button is pressed
         */
        override fun onDestroyActionMode(mode: ActionMode) {
//            vm.deSelectAll()
            actionMode = null
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            return false
        }

    }


}


