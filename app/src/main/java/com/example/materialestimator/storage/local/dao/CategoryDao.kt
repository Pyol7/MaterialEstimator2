package com.example.materialestimator.storage.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.materialestimator.models.entities.Category
import com.example.materialestimator.models.relationships.CategoriesWithMaterials

@Dao
interface CategoryDao {
    @Query("SELECT * FROM category WHERE categoryID = :ID")
    fun get(ID: Int?): LiveData<Category>

    @Query("SELECT * FROM category")
    fun getAll() : LiveData<List<Category>>

    @Transaction
    @Query("SELECT * FROM category")
    fun getCategoriesWithMaterials(): LiveData<List<CategoriesWithMaterials>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(category: Category?)

    @Update
    suspend fun update(category: Category?);

    @Update
    suspend fun updateAll(categories: List<Category>);

    @Query("DELETE FROM category")
    suspend fun clear()
}