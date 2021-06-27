package com.example.materialestimator.storage.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.materialestimator.models.entities.Project

@Dao
interface ProjectDao {
    @Query("SELECT * FROM project WHERE id = :ID")
    fun get(ID: Int?): LiveData<Project>

    @Query("SELECT * FROM project")
    fun getAll() : LiveData<List<Project>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(project: Project?)

    @Update
    suspend fun update(project: Project?)

    @Update
    suspend fun updateAll(projects: List<Project>)

    @Query("DELETE FROM project")
    suspend fun clear()
}