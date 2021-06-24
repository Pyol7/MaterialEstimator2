package com.example.materialestimator.viewModels

import android.app.Application
import androidx.lifecycle.*
import com.example.materialestimator.models.Material
import com.example.materialestimator.storage.local.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MaterialViewModel(application: Application) : AndroidViewModel(application) {
    private val materialDao = AppDatabase.getInstance(application).materialDao()
    var selectedCategoryID = MutableLiveData<Int>()

    /**
     * Observes the _selectedCategoryID and calls getAllByCategoryID(_selectedCategoryID).
     * The transformations are calculated lazily, and will run only when the returned LiveData
     * is observed. Lifecycle behavior is propagated from the input source LiveData to the returned one
     */
    val materials = Transformations.switchMap(selectedCategoryID) { getAllByCategoryID(it) }

    fun setSelectedCategoryID(ID: Int) {
        selectedCategoryID.value = ID
    }

    fun get(ID: Int?): LiveData<Material> {
        return materialDao.get(ID)
    }

    private fun getAll(): LiveData<List<Material>> {
        return materialDao.getAll()
    }

    fun getAllSelected(): LiveData<List<Material>> {
        return materialDao.getAllSelected()
    }

    /**
     * Receives non livedata and returns livedata
     */
    suspend fun getAllSelectedNonLiveData(): List<Material> {
            return materialDao.getAllSelectedNonLiveData()
    }

    private fun getAllByCategoryID(ID: Int): LiveData<List<Material>> {
        return materialDao.getAllByCategoryID(ID)
    }

    fun insert(material: Material?) {
        viewModelScope.launch(Dispatchers.IO) {
            materialDao.insert(material)
        }
    }

    fun update(material: Material?) {
        viewModelScope.launch(Dispatchers.IO) {
            materialDao.update(material)
        }
    }

    fun updateAll(materials: List<Material>) {
        viewModelScope.launch(Dispatchers.IO) {
            materialDao.updateAll(materials)
        }
    }

    fun clearAllSelected() {
        viewModelScope.launch(Dispatchers.IO) {
            val m = materialDao.getAllSelectedNonLiveData().onEach {
                it.selected = false
            }
            materialDao.updateAll(m)
        }
    }

    fun clearAll() {
        viewModelScope.launch(Dispatchers.IO) {
            materialDao.clearAll()
        }
    }

}
