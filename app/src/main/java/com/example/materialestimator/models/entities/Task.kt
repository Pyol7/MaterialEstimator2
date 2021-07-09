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
    var skilledLabour: Int? = 0,
    var unSkilledLabour: Int? = 0,
    val materials: List<Material>? = null,
    var projectID: Int? = 0

) {

}
