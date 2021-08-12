package com.example.materialestimator.storage.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.materialestimator.storage.local.entities.Project
import com.example.materialestimator.storage.local.relationships.ProjectWithTasks

@Dao
interface ProjectDao {

    @Transaction
    @Query("SELECT * FROM project")
    fun getAllProjectsWithTasks(): LiveData<List<ProjectWithTasks>>

    @Transaction
    @Query("SELECT * FROM project WHERE projectId = :id")
    fun get(id: Long?): LiveData<ProjectWithTasks>

    @Query("SELECT * FROM project")
    fun getAll() : LiveData<List<Project>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(projectWithTasks: Project?)

    @Update
    suspend fun update(projectWithTasks: Project?)

    @Delete
    suspend fun delete(projectWithTasks: Project)
}