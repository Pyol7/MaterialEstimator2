package com.example.materialestimator.viewModels

import android.app.Application
import androidx.lifecycle.*
import com.example.materialestimator.models.entities.Project
import com.example.materialestimator.models.entities.Task
import com.example.materialestimator.storage.local.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProjectsViewModel(application: Application) : AndroidViewModel(application) {
    private val projectDao = AppDatabase.getInstance(application).projectDao()

    fun get(ID: Int?): LiveData<Project> {
        return projectDao.get(ID)
    }

    fun getAll(): LiveData<List<Project>> {
        return projectDao.getAll()
    }


    fun insert(project: Project?) {
        viewModelScope.launch(Dispatchers.IO) {
            projectDao.insert(project)
        }
    }

    fun update(project: Project?) {
        viewModelScope.launch(Dispatchers.IO) {
            projectDao.update(project)
        }
    }

    fun updateAll(projects: List<Project>) {
        viewModelScope.launch(Dispatchers.IO) {
            projectDao.updateAll(projects)
        }
    }

    fun clear() {
        viewModelScope.launch(Dispatchers.IO) {
            projectDao.clear()
        }
    }
}
