package com.example.materialestimator.storage.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.materialestimator.models.entities.Tool

@Dao
interface ToolDao {

    @Query("SELECT * FROM tool WHERE id = :ID")
    fun get(ID: Int?): LiveData<Tool>

    @Query("SELECT * FROM tool")
    fun getAll() : LiveData<List<Tool>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tool: Tool?)

    @Update
    suspend fun update(tool: Tool?)

    @Update
    suspend fun updateAll(tools: List<Tool>)

    @Query("DELETE FROM tool")
    suspend fun clear()

}
