package com.example.materialestimator.storage.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.materialestimator.models.entities.Task

@Dao
interface TaskDao {
    @Query("SELECT * FROM task WHERE id = :ID")
    fun get(ID: Int?): LiveData<Task>

    @Query("SELECT * FROM task")
    fun getAll() : LiveData<List<Task>>

    @Query("SELECT * FROM task WHERE projectID = :projectID")
    fun getAllTaskByProjectID(projectID: Int) : LiveData<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task?)

    @Update
    suspend fun update(task: Task?)

    @Update
    suspend fun updateAll(tasks: List<Task>)

    @Query("DELETE FROM task")
    suspend fun clear()
}