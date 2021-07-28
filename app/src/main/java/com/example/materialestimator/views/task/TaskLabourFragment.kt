package com.example.materialestimator.views.task

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.materialestimator.R
import com.example.materialestimator.TAG
import com.example.materialestimator.adapters.ProjectsFragmentAdapter
import com.example.materialestimator.adapters.TaskLabourFragmentRVAdapter
import com.example.materialestimator.viewModels.SharedViewModel
import com.example.materialestimator.viewModels.TaskViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TaskLabourFragment : Fragment(R.layout.fragment_task_labour) {
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val taskVm: TaskViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rvAdapter = TaskLabourFragmentRVAdapter()
        val rv = view.findViewById(R.id.task_labour_rv) as RecyclerView
        rv.apply {
            setHasFixedSize(true)
            adapter = rvAdapter
        }
        sharedViewModel.selectedTask.employees?.let { rvAdapter.setEmployees(it) }
    }

    override fun onPause() {
        super.onPause()
        taskVm.update(sharedViewModel.selectedTask)
    }
}
