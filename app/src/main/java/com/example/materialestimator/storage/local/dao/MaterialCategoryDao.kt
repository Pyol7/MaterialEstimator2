package com.example.materialestimator.storage.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.materialestimator.models.entities.MaterialCategory
import com.example.materialestimator.models.relationships.CategoriesWithMaterials

@Dao
interface MaterialCategoryDao {
    @Query("SELECT * FROM category WHERE id = :ID")
    fun get(ID: Int?): LiveData<MaterialCategory>

    @Query("SELECT * FROM category")
    fun getAll() : LiveData<List<MaterialCategory>>

    @Transaction
    @Query("SELECT * FROM category")
    fun getCategoriesWithMaterials(): LiveData<List<CategoriesWithMaterials>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(materialCategory: MaterialCategory?)

    @Update
    suspend fun update(materialCategory: MaterialCategory?)

    @Update
    suspend fun updateAll(materialCategories: List<MaterialCategory>)

    @Query("DELETE FROM category")
    suspend fun clear()
}