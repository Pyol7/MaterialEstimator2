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
class Employee(
    @PrimaryKey(autoGenerate = true)
    var employeeId: Long = 0,
    var taskId_fk: Long? = null,
    var name: String = "",
    var image: String = ""
)
