package com.example.materialestimator.views.task

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.materialestimator.R
import com.example.materialestimator.TAG
import com.example.materialestimator.adapters.TaskMaterialsFragmentRVAdapter
import com.example.materialestimator.storage.local.entities.Material
import com.example.materialestimator.viewModels.TasksViewModel

/**
 * Displays the list of material needed for the Task.
 * Sends all changes to the parent fragment which in turn updates the task.
 */

class TaskMaterialsFragment : Fragment(R.layout.fragment_task_materials) {
    private val taskVM: TasksViewModel by viewModels()
    private var taskId = 0L

    companion object {
        fun newInstance(taskId: Long?) = TaskMaterialsFragment().apply {
            arguments = bundleOf("key" to taskId)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // If there is a saved id then restore it, else use the one that was passed in.
        taskId = savedInstanceState?.getLong("key") ?: arguments?.getLong("key")!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Views
        val rv = view.findViewById(R.id.task_materials_rv) as RecyclerView

        val rvAdapter = TaskMaterialsFragmentRVAdapter()
        rv.apply {
            setHasFixedSize(true)
            rvAdapter.setOnItemClickListener(object :
                TaskMaterialsFragmentRVAdapter.OnItemClickListener {
                override fun onItemClick(id: Long) {
                    // Navigate to MaterialFragment
                    arguments = bundleOf("key" to id)
                    findNavController().navigate(R.id.action_taskFragment_to_materialFragment, arguments)
                }
            })
            adapter = rvAdapter
        }
        // Get the selected task and display its materials
        taskVM.get(taskId).observe(viewLifecycleOwner){
            rvAdapter.setMaterials(it.materials)
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong("key", taskId)
    }

}
