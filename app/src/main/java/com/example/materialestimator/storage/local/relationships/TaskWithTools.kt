package com.example.materialestimator.storage.local.relationships

import androidx.room.Embedded
import androidx.room.Relation
import com.example.materialestimator.storage.local.entities.Task
import com.example.materialestimator.storage.local.entities.Tool

data class TaskWithTools(
    @Embedded
    val task: Task,
    @Relation(
        parentColumn = "taskId",
        entityColumn = "taskId_fk",
    )
    var tools: MutableList<Tool>
)
