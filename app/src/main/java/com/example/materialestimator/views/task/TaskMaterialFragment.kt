package com.example.materialestimator.views.task

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.materialestimator.R
import com.example.materialestimator.TAG
import com.example.materialestimator.adapters.TaskMaterialFragmentRVAdapter
import com.example.materialestimator.models.entities.Material
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
class TaskMaterialFragment : Fragment(), TaskMaterialFragmentRVAdapter.OnItemClickListener {
    // This vm is scoped to TaskFragment so it is initialized and cleared with it.
    private val taskVm: TaskViewModel by activityViewModels()
    private lateinit var rvAdapter: TaskMaterialFragmentRVAdapter
    private lateinit var addMaterialFab: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_task_material, container, false)
        val rv = view.findViewById<RecyclerView>(R.id.task_material_rv)
        rv.apply {
            setHasFixedSize(true)
            rvAdapter = TaskMaterialFragmentRVAdapter()
            rvAdapter.setOnItemClickListener(this@TaskMaterialFragment)
            adapter = rvAdapter
        }

        addMaterialFab = view.findViewById(R.id.add_material_fab)
        addMaterialFab.visibility = View.VISIBLE
        addMaterialFab.setOnClickListener {
            findNavController().navigate(R.id.action_global_materialsFragment)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Get the selected task set on the vm by TasksFragment() and display the list of materials.
            rvAdapter.setMaterials(taskVm.selectedTask.materials)
    }

    override fun onItemClick(material: Material) {
        Log.i(TAG, "Name = ${material.name}")
    }

    override fun onItemQuantityChange(material: Material) {
        taskVm.updateMaterialOnSelectedTaskMaterials(material)
    }

    override fun onPause() {
        super.onPause()
        taskVm.update(taskVm.selectedTask)
    }

    override fun onDestroy() {
        super.onDestroy()
        addMaterialFab.visibility = View.INVISIBLE
    }

}
