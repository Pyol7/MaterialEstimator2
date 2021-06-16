package com.example.materialestimator.storage.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.materialestimator.models.entities.Material

/**
 * Only LiveData or suspend functions allowed.
 */
@Dao
interface MaterialDao{
    @Query("SELECT * FROM material WHERE materialID = :ID")
    fun get(ID: Int?): LiveData<Material>

    //LiveData is handled on a background thread so there is no need for a suspend function.
    @Query("SELECT * FROM material")
    fun getAll() : LiveData<List<Material>>

    @Query("SELECT * FROM material WHERE selected = 1")
    fun getAllSelected(): LiveData<List<Material>>

    @Query("SELECT * FROM material WHERE selected = 1")
    suspend fun getAllSelectedNonLiveData(): List<Material>

    @Query("SELECT * FROM material WHERE materialCategoryID = :ID")
    fun getAllByCategoryID(ID: Int): LiveData<List<Material>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(material: Material?)

    @Update
    suspend fun update(material: Material?);

    @Update
    suspend fun updateAll(materials: List<Material>);

    @Query("DELETE FROM material")
    suspend fun clearAll()
}