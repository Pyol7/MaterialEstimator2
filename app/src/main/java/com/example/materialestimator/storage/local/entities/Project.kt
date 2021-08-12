package com.example.materialestimator.storage.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Project(
    @PrimaryKey(autoGenerate = true)
    var projectId: Long = 0,
    var name: String = "",
    var image: String = ""
)
