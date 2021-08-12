package com.example.materialestimator.viewModels

import android.app.Application
import androidx.lifecycle.*
import com.example.materialestimator.storage.local.entities.Task
import com.example.materialestimator.storage.local.AppDatabase
import com.example.materialestimator.storage.local.relationships.TaskWithEmployees
import com.example.materialestimator.storage.local.relationships.TaskWithTools
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TasksViewModel(application: Application) : AndroidViewModel(application) {
    private val taskDao = AppDatabase.getInstance(application).taskDao()

    fun getTasksByProjectID(projectId: Long): LiveData<List<Task>> {
        return taskDao.getTasksByProjectID(projectId)
    }

    fun get(id: Long?): LiveData<Task> {
        return taskDao.get(id)
    }

    fun insert(task: Task?) {
        viewModelScope.launch(Dispatchers.IO) {
            taskDao.insert(task)
        }
    }

    fun update(task: Task?) {
        viewModelScope.launch(Dispatchers.IO) {
            taskDao.update(task)
        }
    }

    fun delete(task: Task?) {
        viewModelScope.launch(Dispatchers.IO) {
            taskDao.delete(task)
        }
    }

    fun getTaskWithEmployees(taskId: Long): LiveData<TaskWithEmployees> {
        return taskDao.getTaskWithEmployees(taskId)
    }

    fun getTaskWithTools(taskId: Long): LiveData<TaskWithTools> {
        return taskDao.getTaskWithTools(taskId)
    }

}
