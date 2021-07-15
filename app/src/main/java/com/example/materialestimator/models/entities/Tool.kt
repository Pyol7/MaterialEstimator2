package com.example.materialestimator.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "tool")
class Tool (

    @PrimaryKey(autoGenerate = true)
    var ID: Int = 0,
    var type: String = "",
    var make: String = "",
    var image: String = ""

)
