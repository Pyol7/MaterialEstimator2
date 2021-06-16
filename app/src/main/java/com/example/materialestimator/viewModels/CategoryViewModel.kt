package com.example.materialestimator.viewModels

import android.app.Application
import androidx.lifecycle.*
import com.example.materialestimator.models.entities.Category
import com.example.materialestimator.models.relationships.CategoriesWithMaterials
import com.example.materialestimator.storage.local.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryViewModel(application: Application) : AndroidViewModel(application) {
    private val categoryDao = AppDatabase.getInstance(application).categoryDao()

    fun get(ID: Int?): LiveData<Category> {
        return categoryDao.get(ID)
    }

    fun getAll(): LiveData<List<Category>> {
        return categoryDao.getAll()
    }

    fun getCategoriesWithMaterials(): LiveData<List<CategoriesWithMaterials>> {
        return categoryDao.getCategoriesWithMaterials()
    }

    fun insert(category: Category?) {
        viewModelScope.launch(Dispatchers.IO) {
            categoryDao.insert(category)
        }
    }

    fun update(category: Category?) {
        viewModelScope.launch(Dispatchers.IO) {
            categoryDao.update(category)
        }
    }

    fun updateAll(categories: List<Category>) {
        viewModelScope.launch(Dispatchers.IO) {
            categoryDao.updateAll(categories)
        }
    }

    fun clear() {
        viewModelScope.launch(Dispatchers.IO) {
            categoryDao.clear()
        }
    }
}
