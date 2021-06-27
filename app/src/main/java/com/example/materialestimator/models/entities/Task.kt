package com.example.materialestimator.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
@Entity(tableName = "task")
class Task (

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var title: String = "",
    var desc: String = "",
    var startDate: Date? = null,
    var endDate: Date? = null,
    var percentCompleted: Int = 0,
    var projectid: Int = 0

)
