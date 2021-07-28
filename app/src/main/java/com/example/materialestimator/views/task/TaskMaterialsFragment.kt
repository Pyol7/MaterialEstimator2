package com.example.materialestimator.views.task

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.materialestimator.R
import com.example.materialestimator.TAG
import com.example.materialestimator.adapters.TaskMaterialFragmentRVAdapter
import com.example.materialestimator.models.entities.Material
import com.example.materialestimator.models.entities.Task
import com.example.materialestimator.viewModels.SharedViewModel
import com.example.materialestimator.viewModels.TaskViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * Responsible for displaying the task's list of material.
 * It creates an instance of the MaterialsViewModel and scopes it to the MainActivity scope.
 * This makes it possible to navigate the the MaterialsFragment class (which builds a material list),
 * then returns back to this class and have that same list available for observation.
 * That list would only be cleared if the hosting Activity dies or it is explicitly cleared as in
 * this case when the Task is saved.
 */
class TaskMaterialsFragment : Fragment(R.layout.fragment_task_material) {
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val taskVm: TaskViewModel by viewModels()
    private lateinit var task: Task

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        task = sharedViewModel.selectedTask
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rvAdapter = TaskMaterialFragmentRVAdapter()
        val rv = view.findViewById<RecyclerView>(R.id.task_materials_rv)
        rv.apply {
            setHasFixedSize(true)
            rvAdapter.setOnItemClickListener(object : TaskMaterialFragmentRVAdapter.OnItemClickListener{
                override fun onItemClick(material: Material) {
                    Log.i(TAG, "Name = ${material.name}")
                }

                override fun onItemQuantityChange(material: Material) {
                    sharedViewModel.updateMaterialOnSelectedTaskMaterials(material)
                }

            })
            adapter = rvAdapter
        }
        val addMaterialFab = view.findViewById<FloatingActionButton>(R.id.add_material_fab)
        addMaterialFab.setOnClickListener {
            findNavController().navigate(R.id.action_global_materialsFragment)
        }
        rvAdapter.setMaterials(task.materials)
    }

    override fun onPause() {
        super.onPause()
        taskVm.update(task)
    }

}
