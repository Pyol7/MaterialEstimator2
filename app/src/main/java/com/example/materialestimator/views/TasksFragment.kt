package com.example.materialestimator.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.materialestimator.R
import com.example.materialestimator.adapters.TasksFragmentRVAdapter
import com.example.materialestimator.models.entities.Task
import com.example.materialestimator.viewModels.TaskViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 *
 */
class TasksFragment : Fragment(){
    private val taskVm: TaskViewModel by activityViewModels()
    private var rvAdapter: TasksFragmentRVAdapter? = null
    private lateinit var toolbar: Toolbar
    private var projectID = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        projectID = arguments?.getInt("Key")!!
        val view = inflater.inflate(R.layout.fragment_tasks, container, false)
        // Use this fragment's toolbar and provide all functionality
        toolbar = view.findViewById(R.id.tasks_toolbar) as Toolbar
        toolbar.inflateMenu(R.menu.general_toolbar_menu)
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        val rv = view.findViewById(R.id.task_rv) as RecyclerView
        rv.apply {
            setHasFixedSize(true)
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
            rvAdapter = TasksFragmentRVAdapter()
            rvAdapter!!.setOnItemClickListener(object : TasksFragmentRVAdapter.OnItemClickListener {
                override fun onItemClick(task: Task) {
                    // Store the selected task on the vm
                    taskVm.selectedTask = task
                    findNavController().navigate(R.id.action_tasksFragment_to_taskViewPagerFragment)
                }
            })
            adapter = rvAdapter
        }
        taskVm.getAllTaskByProjectID(projectID).observe(viewLifecycleOwner) {
            rvAdapter?.setTasks(it)
        }
        val addTaskFab = view.findViewById(R.id.add_task_fab) as FloatingActionButton
        addTaskFab.setOnClickListener() {
            // Create a new task and ......
//            val bundle = bundleOf("projectID" to projectID)
//            findNavController().navigate(R.id.action_tasksFragment_to_taskViewPagerFragment, bundle)
        }

        return view
    }
}