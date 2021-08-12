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
import com.example.materialestimator.adapters.TaskEmployeesFragmentRVAdapter
import com.example.materialestimator.viewModels.TasksViewModel

/**
 * Displays the list of employees needed for the Task.
 */

class TaskEmployeesFragment : Fragment(R.layout.fragment_task_employees) {
    private val tasksVm: TasksViewModel by viewModels()
    private var taskId = 0L

    companion object {
        fun newInstance(taskId: Long?) = TaskEmployeesFragment().apply {
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
        val rv = view.findViewById(R.id.task_employee_rv) as RecyclerView
        // Adapter
        val rvAdapter = TaskEmployeesFragmentRVAdapter()
        rvAdapter.setOnItemClickListener(object :
            TaskEmployeesFragmentRVAdapter.OnItemClickListener {
            override fun onItemClick(id: Long) {
                arguments = bundleOf("key" to id)
                findNavController().navigate(
                    R.id.action_taskFragment_to_employeeFragment,
                    arguments
                )
            }

        })
        // RecyclerView
        rv.apply {
            setHasFixedSize(true)
            adapter = rvAdapter
        }
        // Load employees
        tasksVm.getTaskWithEmployees(taskId).observe(viewLifecycleOwner) {
            rvAdapter.setEmployees(it.employees)
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong("key", taskId)
    }

}
