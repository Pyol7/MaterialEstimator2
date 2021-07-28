package com.example.materialestimator.views.task

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.materialestimator.R
import com.example.materialestimator.adapters.TaskToolFragmentRVAdapter
import com.example.materialestimator.utilities.MoshiConverters
import com.example.materialestimator.viewModels.SharedViewModel
import com.example.materialestimator.viewModels.TaskViewModel

class TaskToolsFragment : Fragment(R.layout.fragment_task_tools) {
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val taskVm: TaskViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rvAdapter = TaskToolFragmentRVAdapter()
        rvAdapter.setOnItemClickListener(object : TaskToolFragmentRVAdapter.OnItemClickListener{
            override fun onItemClick(jsonTool: String) {
                // Store the selected tool on the vm and navigate to the tool fragment
                sharedViewModel.tool = MoshiConverters.jsonToTool(jsonTool)!!
                findNavController().navigate(R.id.action_taskFragment_to_toolFragment)
            }
        })
        val rv = view.findViewById(R.id.task_tools_rv) as RecyclerView
        rv.apply {
            setHasFixedSize(true)
            adapter = rvAdapter
        }
        sharedViewModel.selectedTask.tools?.let { rvAdapter.setTools(it) }
    }

    override fun onPause() {
        super.onPause()
        taskVm.update(sharedViewModel.selectedTask)
    }
}
