package com.example.materialestimator.storage.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.materialestimator.storage.local.entities.Material

/**
 * Only LiveData or suspend functions allowed.
 */
@Dao
interface MaterialDao{

    @Query("SELECT * FROM material WHERE materialId = :ID")
    fun get(ID: Long?): LiveData<Material>

    //LiveData is handled on a background thread so there is no need for a suspend function.
    @Query("SELECT * FROM material")
    fun getAll() : LiveData<List<Material>>

    @Query("SELECT * FROM material WHERE selected = 1")
    fun getAllSelected(): LiveData<List<Material>>

    @Query("SELECT * FROM material WHERE selected = 1")
    suspend fun getAllSelectedNonLiveData(): List<Material>

    @Query("SELECT * FROM material WHERE materialCategoryId = :ID")
    fun getAllByCategoryID(ID: Long): LiveData<List<Material>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(material: Material?)

    @Update
    suspend fun update(material: Material?)

    @Delete
    suspend fun delete(material: Material?)

}