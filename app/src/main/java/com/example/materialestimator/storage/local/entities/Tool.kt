package com.example.materialestimator.storage.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Task::class,
            parentColumns = ["taskId"],
            childColumns = ["taskId_fk"],
            onDelete = CASCADE
        )
    ],
    indices = [
        Index(
            value = ["taskId_fk"]
        )
    ]
)
class Tool (
    @PrimaryKey(autoGenerate = true)
    var toolId: Long = 0,
    var taskId_fk: Long? = null,
    var image: String = "",
    var type: String = "",
    var make: String = ""
)
