package com.example.materialestimator.views

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.LayoutInflater
import android.widget.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.materialestimator.R
import com.example.materialestimator.TAG
import com.example.materialestimator.adapters.MaterialsFragmentListAdapter
import com.example.materialestimator.models.entities.Category
import com.example.materialestimator.models.entities.Material
import com.example.materialestimator.utilities.Converters
import com.example.materialestimator.viewModels.CategoryViewModel
import com.example.materialestimator.viewModels.MaterialViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class MaterialsFragment : Fragment(R.layout.fragment_materials),
    MaterialsFragmentListAdapter.OnItemClickListener {
    private val materialVm: MaterialViewModel by viewModels()
    private val categoryVm: CategoryViewModel by viewModels()
    private val actionModeCallback: ActionMode.Callback = ActionModeCallback()
    private var actionMode: ActionMode? = null
    private var rvAdapter: MaterialsFragmentListAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rv = view.findViewById(R.id.materials_rv) as RecyclerView
        rv.apply {
            layoutManager = LinearLayoutManager(layoutInflater.context)
            setHasFixedSize(true)
            rvAdapter = MaterialsFragmentListAdapter()
            rvAdapter!!.setOnItemClickListener(this@MaterialsFragment)
            adapter = rvAdapter
            adapter?.stateRestorationPolicy =
                RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        }

        val spinner = view.findViewById(R.id.spinner) as Spinner
        val spinnerAdapter = CategoriesAdapter(requireContext())
        spinner.adapter = spinnerAdapter

        categoryVm.getAll().observe(viewLifecycleOwner) { categories ->
            spinnerAdapter.setList(categories)
            materialVm.selectedCategoryID.observe(viewLifecycleOwner) { ID ->
                spinner.setSelection(ID.minus(1))
            }
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    materialVm.setSelectedCategoryID(categories[position].categoryID)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }

        materialVm.materials.observe(viewLifecycleOwner) {
            rvAdapter?.setList(it)
        }

        materialVm.getAllSelected().observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                if (actionMode == null) {
                    actionMode = activity?.startActionMode(actionModeCallback)
                }
                actionMode?.title = it.size.toString()
            } else {
                actionMode?.finish()
            }
        }

    }

    override fun onItemSelected(json: String) {
        val material = Converters.jsonToMaterial(json)
        materialVm.update(material)
    }

    override fun onItemClicked(ID: Int) {
        actionMode?.finish()
        val bundle = bundleOf("Key" to ID)
        view?.findNavController()
            ?.navigate(R.id.action_materialListFragment_to_materialFragment, bundle)
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
            idView.text = category?.categoryID.toString()
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
                    // Create custom list of material
                    viewLifecycleOwner.lifecycleScope.launch {
                        val newList = Converters.convertListOfBaseTypeToListOfSubtypes(
                            materialVm.getAllSelectedNonLiveData()
                        )
                        val json = Converters.materialListToJson(newList)
                        val bundle = bundleOf("Key" to json)
                        view?.findNavController()
                            ?.navigate(R.id.action_materialsFragment_to_materialCalculatorFragment, bundle)
                    }
                    mode.finish()
                    true
                }

                else -> false
            }
        }

        /**
         * Called when context menu up button is pressed
         */
        override fun onDestroyActionMode(mode: ActionMode) {
            materialVm.clearAllSelected()
            actionMode = null
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            return false
        }

    }


}


