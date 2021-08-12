package com.example.materialestimator.viewModels

import android.app.Application
import androidx.lifecycle.*
import com.example.materialestimator.storage.local.entities.*
import com.example.materialestimator.storage.local.AppDatabase

class SharedViewModel(application: Application) : AndroidViewModel(application) {
    private val projectDao = AppDatabase.getInstance(application).projectDao()
    var selectedMaterials: MutableList<Material>? = null

    var selectedProjectId = MutableLiveData<Long>()
    // Loads the selected project when ever the selectedProjectId changes
    val selectedProject = Transformations.switchMap(selectedProjectId) {
        projectDao.get(it)
    }

    var selectedEmployee = Employee()
    var selectedTool = Tool()

    fun updateMaterialOnSelectedTaskMaterials(material: Material){
//        val materials = selectedTask.materials
//        materials?.set(materials.indexOf(material), material)
    }

    fun updateToolOnSelectedTaskTools(tool: Tool){
//        val tools = selectedTask.tools
//        tools?.set(tools.indexOf(tool), tool)
    }

    fun addMaterialsToSelectedTaskMaterials(materials: List<Material>){
//        for (m in materials){
//            selectedTask.materials?.add(m)
//        }
    }

}
