package com.example.materialestimator.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity
data class Category(

    @PrimaryKey(autoGenerate = true)
    val categoryID: Int = 0,
    var name: String = ""

)
