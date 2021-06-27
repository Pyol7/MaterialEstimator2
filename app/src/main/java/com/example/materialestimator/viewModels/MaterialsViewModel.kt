package com.example.materialestimator.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.materialestimator.TAG
import com.example.materialestimator.models.entities.Category
import com.example.materialestimator.models.entities.Material
import com.example.materialestimator.storage.local.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MaterialsViewModel(application: Application) : AndroidViewModel(application) {
    private val materialDao = AppDatabase.getInstance(application).materialDao()
    private val categoryDao = AppDatabase.getInstance(application).categoryDao()

    var selectedCategoryID = MutableLiveData<Int>()

    /**
     * Builds a LiveData that has values yielded from the given [block] that executes on a
     * [LiveDataScope].
     * emitSource - Sets a source for the LiveData builder.
     * Whenever the source has a new value, selectedMaterials receives and emits it.
     * emit - Emits a single value to the LiveData’s active observers.
     * If emitSource(source) is executed before, it would remove it's source.
     * This means that even when emitSource(source) value changes,
     * selectedMaterials no longer emits that new value.
     */
    val selectedMaterials =  liveData {
        emitSource(materialDao.getAllSelected())
    }

    fun getAllCategories(): LiveData<List<Category>> {
        return categoryDao.getAll()
    }

    /**
     * Observes the selectedCategoryID and calls getAllByCategoryID(selectedCategoryID).
     * The transformations are calculated lazily, and will run only when the returned LiveData
     * is observed. Lifecycle behavior is propagated from the input source LiveData to the returned one
     */
    val materialsByCategoryId = Transformations.switchMap(selectedCategoryID) { getAllByCategoryID(it) }

    fun get(ID: Int?): LiveData<Material> {
        return materialDao.get(ID)
    }

    /**
     * Receives a List<Material> asynchronously for one time use (Not LiveData)
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
