package com.example.materialestimator.views.task

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.materialestimator.R
import com.example.materialestimator.TAG
import com.example.materialestimator.viewModels.TaskViewModel

class TaskLabourFragment: Fragment(R.layout.fragment_task_labour) {
    // This vm is scoped to TaskFragment so it is initialized and cleared with it.
    private val vm: TaskViewModel by viewModels({ requireParentFragment() })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Get the selected task set on the vm by TasksFragment() and display the list of labour
        vm.selectedTask.observe(viewLifecycleOwner) {
            Log.i(TAG, "TaskLabourFragment: selectedTask = $it")
        }
    }
}
