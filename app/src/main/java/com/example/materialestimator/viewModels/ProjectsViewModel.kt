package com.example.materialestimator.viewModels

import android.app.Application
import androidx.lifecycle.*
import com.example.materialestimator.storage.local.entities.Project
import com.example.materialestimator.storage.local.AppDatabase
import com.example.materialestimator.storage.local.relationships.ProjectWithTasks
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProjectsViewModel(application: Application) : AndroidViewModel(application) {
    private val projectDao = AppDatabase.getInstance(application).projectDao()

    fun getAllProjectsWithTasks(): LiveData<List<ProjectWithTasks>> {
        return projectDao.getAllProjectsWithTasks()
    }

    fun get(ID: Long?): LiveData<ProjectWithTasks> {
        return projectDao.get(ID)
    }

    fun getAll(): LiveData<List<Project>> {
        return projectDao.getAll()
    }

    fun insert(projectWithTasks: Project?) {
        viewModelScope.launch(Dispatchers.IO) {
            projectDao.insert(projectWithTasks)
        }
    }

    fun update(projectWithTasks: Project?) {
        viewModelScope.launch(Dispatchers.IO) {
            projectDao.update(projectWithTasks)
        }
    }

    fun delete(projectWithTasks: Project) {
        viewModelScope.launch(Dispatchers.IO) {
            projectDao.delete(projectWithTasks)
        }
    }
}
