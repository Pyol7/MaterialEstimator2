package com.example.materialestimator.storage.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.materialestimator.models.entities.Employee

@Dao
interface EmployeeDao {

    @Query("SELECT * FROM employee WHERE id = :ID")
    fun get(ID: Int?): LiveData<Employee>

    @Query("SELECT * FROM employee")
    fun getAll() : LiveData<List<Employee>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(employee: Employee?)

    @Update
    suspend fun update(employee: Employee?)

    @Update
    suspend fun updateAll(categories: List<Employee>)

    @Query("DELETE FROM employee")
    suspend fun clear()

}
