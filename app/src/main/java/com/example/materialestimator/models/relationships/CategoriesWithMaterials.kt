package com.example.materialestimator.models.relationships

import androidx.room.Embedded
import androidx.room.Relation
import com.example.materialestimator.models.entities.MaterialCategory
import com.example.materialestimator.models.entities.Material

data class CategoriesWithMaterials(
    @Embedded
    val materialCategory: MaterialCategory,
    @Relation(
        parentColumn = "ID",
        entityColumn = "categoryID"
    )
    val materials: List<Material>
)
