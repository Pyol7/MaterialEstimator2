package com.example.materialestimator.views.task

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.materialestimator.R
import com.example.materialestimator.TAG
import com.example.materialestimator.adapters.TaskToolsFragmentRVAdapter
import com.example.materialestimator.utilities.MoshiConverters
import com.example.materialestimator.viewModels.SharedViewModel
import com.example.materialestimator.viewModels.TasksViewModel

/**
 * Displays the list of tools needed for the Task.
 */

class TaskToolsFragment : Fragment(R.layout.fragment_task_tools) {
    private val tasksVm: TasksViewModel by viewModels()
    private var taskId = 0L

    companion object {
        fun newInstance(taskId: Long?) = TaskToolsFragment().apply {
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
        val rvAdapter = TaskToolsFragmentRVAdapter()
        rvAdapter.setOnItemClickListener(object : TaskToolsFragmentRVAdapter.OnItemClickListener{
            override fun onItemClick(id: Long) {
                // Navigate to ToolFragment
                arguments = bundleOf("key" to id)
                findNavController().navigate(R.id.action_taskFragment_to_toolFragment, arguments)
            }
        })
        val rv = view.findViewById(R.id.task_tools_rv) as RecyclerView
        rv.apply {
            setHasFixedSize(true)
            adapter = rvAdapter
        }

        // Load tools
        tasksVm.getTaskWithTools(taskId).observe(viewLifecycleOwner) {
            rvAdapter.setTools(it.tools)
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong("key", taskId)
    }

}
