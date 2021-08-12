package com.example.materialestimator.storage.local.relationships

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.materialestimator.storage.local.entities.Employee
import com.example.materialestimator.storage.local.entities.Material
import com.example.materialestimator.storage.local.entities.Task
import com.example.materialestimator.storage.local.entities.Tool

data class TaskWithEmployees(
    @Embedded
    val task: Task,
    @Relation(
        parentColumn = "taskId",
        entityColumn = "taskId_fk",
    )
    var employees: MutableList<Employee>
)
