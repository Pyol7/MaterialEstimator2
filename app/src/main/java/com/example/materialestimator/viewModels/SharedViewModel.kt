package com.example.materialestimator.viewModels

import androidx.lifecycle.*
import com.example.materialestimator.models.entities.Material
import com.example.materialestimator.models.entities.Project
import com.example.materialestimator.models.entities.Task
import com.example.materialestimator.models.entities.Tool

class SharedViewModel() : ViewModel() {

    var selectedProject = Project()
    var selectedTask = Task()
    var tool = Tool()

    fun updateMaterialOnSelectedTaskMaterials(material: Material){
        val materials = selectedTask.materials
        materials?.set(materials.indexOf(material), material)
    }

    fun addMaterialsToSelectedTaskMaterials(materials: List<Material>){
        for (m in materials){
            selectedTask.materials?.add(m)
        }
    }

}
