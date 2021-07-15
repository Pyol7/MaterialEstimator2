package com.example.materialestimator.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.materialestimator.TAG
import com.example.materialestimator.models.entities.Material
import com.example.materialestimator.models.entities.Task
import com.example.materialestimator.storage.local.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val taskDao = AppDatabase.getInstance(application).taskDao()

    fun getAllTaskByProjectID(projectID: Int): LiveData<List<Task>> {
        return taskDao.getAllTaskByProjectID(projectID)
    }

    fun get(ID: Int?): LiveData<Task> {
        return taskDao.get(ID)
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

    fun updateAll(tasks: List<Task>) {
        viewModelScope.launch(Dispatchers.IO) {
            taskDao.updateAll(tasks)
        }
    }

    fun clear() {
        viewModelScope.launch(Dispatchers.IO) {
            taskDao.clear()
        }
    }

}
