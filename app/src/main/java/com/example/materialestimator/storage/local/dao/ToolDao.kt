package com.example.materialestimator.storage.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.materialestimator.storage.local.entities.Tool

@Dao
interface ToolDao {

    @Query("SELECT * FROM tool WHERE toolId = :id")
    fun get(id: Long?): LiveData<Tool?>

    @Query("SELECT * FROM tool")
    fun getAll() : LiveData<List<Tool>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tool: Tool?)

    @Update
    suspend fun update(tool: Tool?)

    @Delete
    suspend fun delete(tool: Tool?)

}
