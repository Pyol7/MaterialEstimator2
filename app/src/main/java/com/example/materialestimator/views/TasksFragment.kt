package com.example.materialestimator.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.materialestimator.R
import com.example.materialestimator.TAG
import com.example.materialestimator.adapters.TasksFragmentRVAdapter
import com.example.materialestimator.models.entities.Project
import com.example.materialestimator.models.entities.Task
import com.example.materialestimator.viewModels.SharedViewModel
import com.example.materialestimator.viewModels.TaskViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 *
 */
class TasksFragment : Fragment(R.layout.fragment_tasks){
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val taskVm: TaskViewModel by viewModels()
    private lateinit var project: Project

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        project = sharedViewModel.selectedProject
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view.findViewById(R.id.tasks_toolbar) as Toolbar
        toolbar.title = "Tasks"
        toolbar.subtitle = project.name
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        toolbar.inflateMenu(R.menu.general_toolbar_menu)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_add -> {
                    Log.i(TAG, "Add task...")
                    true
                }
                R.id.action_settings -> {
                    Log.i(TAG, "Settings...")
                    true
                }
                R.id.action_help -> {
                    Log.i(TAG, "Help...")
                    true
                }
                else -> {
                    super.onOptionsItemSelected(it)
                }
            }
        }
        val rvAdapter = TasksFragmentRVAdapter()
        val rv = view.findViewById(R.id.task_rv) as RecyclerView
        rv.apply {
            setHasFixedSize(true)
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
            rvAdapter.setOnItemClickListener(object : TasksFragmentRVAdapter.OnItemClickListener {
                override fun onItemClick(task: Task) {
                    // Store the selected task on the vm
                    sharedViewModel.selectedTask = task
                    findNavController().navigate(R.id.action_tasksFragment_to_taskViewPagerFragment)
                }
            })
            adapter = rvAdapter
        }
        taskVm.getAllTaskByProjectID(project.ID).observe(viewLifecycleOwner) {
            rvAdapter.setTasks(it)
        }
    }

}