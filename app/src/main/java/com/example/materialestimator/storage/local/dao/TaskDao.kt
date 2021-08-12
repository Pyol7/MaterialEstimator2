package com.example.materialestimator.storage.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.materialestimator.storage.local.entities.Task
import com.example.materialestimator.storage.local.relationships.TaskWithEmployees
import com.example.materialestimator.storage.local.relationships.TaskWithTools

@Dao
interface TaskDao {
    @Query("SELECT * FROM task WHERE taskId = :ID")
    fun get(ID: Long?): LiveData<Task>

    @Query("SELECT * FROM task")
    fun getAll() : LiveData<List<Task>>

    @Query("SELECT * FROM task WHERE projectId_fk = :projectId")
    fun getTasksByProjectID(projectId: Long) : LiveData<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task?)

    @Update
    suspend fun update(task: Task?)

    @Delete
    suspend fun delete(task:Task?)

    @Query("SELECT * FROM task WHERE taskId = :taskId")
    @Transaction
    fun getTaskWithEmployees(taskId: Long): LiveData<TaskWithEmployees>

    @Query("SELECT * FROM task WHERE taskId = :taskId")
    @Transaction
    fun getTaskWithTools(taskId: Long): LiveData<TaskWithTools>

}