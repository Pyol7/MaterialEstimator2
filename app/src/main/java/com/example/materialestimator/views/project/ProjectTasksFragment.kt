package com.example.materialestimator.views.project

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.materialestimator.R
import com.example.materialestimator.adapters.TasksFragmentRVAdapter
import com.example.materialestimator.viewModels.TasksViewModel

class ProjectTasksFragment : Fragment(R.layout.fragment_project_tasks) {
    private val tasksVm: TasksViewModel by viewModels()
    private var projectId = 0L

    companion object {
        fun newInstance(projectId: Long?) = ProjectTasksFragment().apply {
            arguments = bundleOf("key" to projectId)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Get the selected project id passed in via construction injection
        projectId = arguments?.getLong("key")!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Views
        val rv = view.findViewById(R.id.tasks_rv) as RecyclerView
        // Adapter
        val rvAdapter = TasksFragmentRVAdapter()
        rvAdapter.setOnItemClickListener(object : TasksFragmentRVAdapter.OnItemClickListener {
            override fun onItemClick(taskId: Long?) {
                // Store the selected task on the vm and navigate to the task view pager
                arguments = bundleOf("key" to taskId)
                findNavController().navigate(R.id.action_projectFragment_to_taskFragment, arguments)
            }
        })
        // RecyclerView
        rv.apply {
            setHasFixedSize(true)
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
            layoutManager = LinearLayoutManager(context)
            adapter = rvAdapter
        }
        // Set RecyclerView Data
        tasksVm.getTasksByProjectID(projectId).observe(viewLifecycleOwner) {
            rvAdapter.setTasks(it)
        }
    }

}