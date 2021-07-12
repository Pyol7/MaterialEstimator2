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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.materialestimator.R
import com.example.materialestimator.TAG
import com.example.materialestimator.adapters.ProjectsFragmentAdapter
import com.example.materialestimator.adapters.TaskLabourFragmentRVAdapter
import com.example.materialestimator.viewModels.TaskViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TaskLabourFragment : Fragment(R.layout.fragment_task_labour) {
    // This vm is scoped to TaskFragment so it is initialized and cleared with it.
    private val taskVm: TaskViewModel by activityViewModels()
    private var rvAdapter: TaskLabourFragmentRVAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_task_labour, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rv = view.findViewById(R.id.task_labour_rv) as RecyclerView
        rv.apply {
            setHasFixedSize(true)
            rvAdapter = TaskLabourFragmentRVAdapter()
            adapter = rvAdapter
        }
        taskVm.selectedTask.employees?.let { rvAdapter?.setEmployees(it) }

        // Setup a listener on the fab to show the add project fragment
        val fab = view.findViewById(R.id.add_labour_fab) as FloatingActionButton
        fab.setOnClickListener {
            Toast.makeText(context, "Create new employee...", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPause() {
        super.onPause()
        taskVm.update(taskVm.selectedTask)
    }
}
