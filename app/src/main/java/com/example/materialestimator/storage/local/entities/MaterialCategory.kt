package com.example.materialestimator.storage.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity
class MaterialCategory(

    @PrimaryKey(autoGenerate = true)
    var materialCategoryId: Long = 0,
    var name: String = ""

)
