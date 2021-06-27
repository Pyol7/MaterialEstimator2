package com.example.materialestimator.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import kotlin.collections.ArrayList

@JsonClass(generateAdapter = true)
@Entity(tableName = "project")
class Project(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String = "",
    var image: String = "",

) {

}
