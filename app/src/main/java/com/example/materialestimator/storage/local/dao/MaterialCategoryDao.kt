package com.example.materialestimator.storage.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.materialestimator.storage.local.entities.MaterialCategory
import com.example.materialestimator.storage.local.relationships.CategoriesWithMaterials

@Dao
interface MaterialCategoryDao {
    @Query("SELECT * FROM materialcategory WHERE materialCategoryId = :ID")
    fun get(ID: Long?): LiveData<MaterialCategory>

    @Query("SELECT * FROM materialcategory")
    fun getAll() : LiveData<List<MaterialCategory>>

    @Transaction
    @Query("SELECT * FROM materialcategory")
    fun getCategoriesWithMaterials(): LiveData<List<CategoriesWithMaterials>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(materialCategory: MaterialCategory?)

    @Update
    suspend fun update(materialCategory: MaterialCategory?)

    @Delete
    suspend fun delete(materialCategory: MaterialCategory?)
}