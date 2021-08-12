package com.example.materialestimator.storage.local.relationships

import androidx.room.Embedded
import androidx.room.Relation
import com.example.materialestimator.storage.local.entities.Project
import com.example.materialestimator.storage.local.entities.Task

data class ProjectWithTasks(
    @Embedded
    val project: Project? = null,
    @Relation(
        parentColumn = "projectId",
        entityColumn = "projectId_fk"
    )
    val tasks: List<Task>? = null
)
