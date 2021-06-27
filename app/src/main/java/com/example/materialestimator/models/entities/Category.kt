package com.example.materialestimator.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "category")
class Category(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String = ""

)
