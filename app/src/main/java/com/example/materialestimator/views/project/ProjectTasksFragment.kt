package com.example.materialestimator.views.project

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.materialestimator.R
import com.example.materialestimator.adapters.TasksFragmentRVAdapter
import com.example.materialestimator.models.entities.Project
import com.example.materialestimator.models.entities.Task
import com.example.materialestimator.viewModels.SharedViewModel
import com.example.materialestimator.viewModels.TaskViewModel

class ProjectTasksFragment : Fragment(R.layout.fragment_project_tasks) {
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val taskVm: TaskViewModel by viewModels()
    private lateinit var project: Project

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Get the selected project
        project = sharedViewModel.selectedProject
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Adapter
        val rvAdapter = TasksFragmentRVAdapter()
        rvAdapter.setOnItemClickListener( object : TasksFragmentRVAdapter.OnItemClickListener {
            override fun onItemClick(task: Task) {
                // Store the selected task on the vm and navigate to the task view pager
                sharedViewModel.selectedTask = task
                findNavController().navigate(R.id.action_projectFragment_to_taskFragment)
            }
        })
        // RecyclerView
        val rv = view.findViewById(R.id.tasks_rv) as RecyclerView
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
        // RecyclerView Data
        taskVm.getAllTaskByProjectID(project.ID).observe(viewLifecycleOwner) {
            rvAdapter.setTasks(it)
        }



        }

}