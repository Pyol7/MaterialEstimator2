package com.example.materialestimator.viewModels

import android.app.Application
import androidx.lifecycle.*
import com.example.materialestimator.storage.local.entities.Tool
import com.example.materialestimator.storage.local.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ToolsViewModel(application: Application) : AndroidViewModel(application) {
    private val toolDao = AppDatabase.getInstance(application).toolDao()

    fun get(ID: Long?): LiveData<Tool?> {
        return toolDao.get(ID)
    }

    fun getAll(): LiveData<List<Tool>> {
        return toolDao.getAll()
    }

    fun insert(tool: Tool?) {
        viewModelScope.launch(Dispatchers.IO) {
            toolDao.insert(tool)
        }
    }

    fun update(tool: Tool?) {
        viewModelScope.launch(Dispatchers.IO) {
            toolDao.update(tool)
        }
    }

    fun delete(tool: Tool?) {
        viewModelScope.launch(Dispatchers.IO) {
            toolDao.delete(tool)
        }
    }
}
