package com.example.materialestimator.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
@Entity(tableName = "task")
class Task(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var title: String = "",
    var description: String = "",
    var estimatedDays: Int? = 0,
    var estimatedHours: Int? = 0,
    var skilledLabour: Int? = 0,
    var unSkilledLabour: Int? = 0,
    var percentCompleted: Int? = 0,
    var startDate: Date? = null,
    var completionDate: Date? = null,
    var projectId: Int = 0

) {

}
