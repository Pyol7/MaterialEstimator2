package com.example.materialestimator.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
@Entity(tableName = "task")
class Task(

    @PrimaryKey(autoGenerate = true)
    var ID: Int = 0,
    var title: String = "",
    var description: String = "",
    var startDate: Date? = null,
    var estimatedDays: Int? = 0,
    var estimatedHours: Int? = 0,
    var completionDate: Date? = null,
    var percentCompleted: Int? = 0,
    var materials: MutableList<Material>? = null,
    var employees: MutableList<Employee>? = null,
    var tools: MutableList<Tool>? = null,
    var projectID: Int? = 0

)
