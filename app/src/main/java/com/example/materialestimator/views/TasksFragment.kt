package com.example.materialestimator.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.materialestimator.R
import com.example.materialestimator.adapters.TasksFragmentAdapter
import com.example.materialestimator.viewModels.ProjectsViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton


class TasksFragment : Fragment(){
    private val vm: ProjectsViewModel by viewModels()
    private var rvAdapter: TasksFragmentAdapter? = null
    private lateinit var toolbar: Toolbar
    private var projectId = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_tasks, container, false)

        projectId = arguments?.getInt("Key")!!

        // Use this fragment's toolbar and provide all functionality
        toolbar = view.findViewById(R.id.task_toolbar) as Toolbar
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
            rvAdapter = TasksFragmentAdapter()
            rvAdapter!!.setOnItemClickListener(object : TasksFragmentAdapter.OnItemClickListener {
                override fun onItemClick(taskId: Int) {
                    Toast.makeText(requireContext(), "Working...", Toast.LENGTH_LONG).show();
                }
            })
            adapter = rvAdapter
        }

        vm.getAllTasksByProjectId(projectId).observe(viewLifecycleOwner) {
            rvAdapter?.setTasks(it)
        }

        val addTaskFab = view.findViewById(R.id.add_task_fab) as FloatingActionButton
        addTaskFab.setOnClickListener() {
            val bundle = bundleOf("Key" to projectId)
            findNavController().navigate(R.id.action_tasksFragment_to_TaskFragment, bundle)
        }

        return view
    }
}