package com.example.materialestimator.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity
class Category(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var name: String = ""

)
