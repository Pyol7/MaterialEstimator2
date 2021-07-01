package com.example.materialestimator.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.materialestimator.R
import com.example.materialestimator.adapters.TaskMaterialFragmentListAdapter
import com.example.materialestimator.utilities.MoshiConverters

class TaskMaterialFragment: Fragment() {
    lateinit var adapter: TaskMaterialFragmentListAdapter

    companion object {
        private const val ARG_POSITION = "position"

        fun getInstance(position: Int): Fragment {
            val taskMaterialFragment = TaskMaterialFragment()
            val bundle = Bundle()
            bundle.putInt(ARG_POSITION, position)
            taskMaterialFragment.arguments = bundle
            return taskMaterialFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_task_material, container, false)
        val materialRv = view.findViewById<RecyclerView>(R.id.task_material_rv)
        adapter = TaskMaterialFragmentListAdapter()
        materialRv.adapter = adapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val json = arguments?.getString("Key")
        val materials = MoshiConverters.jsonToMaterialList(json)
        adapter.setMaterials(materials)

    }
}
