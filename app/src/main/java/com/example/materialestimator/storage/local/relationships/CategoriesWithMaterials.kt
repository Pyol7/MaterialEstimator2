package com.example.materialestimator.storage.local.relationships

import androidx.room.Embedded
import androidx.room.Relation
import com.example.materialestimator.storage.local.entities.MaterialCategory
import com.example.materialestimator.storage.local.entities.Material

data class CategoriesWithMaterials(
    @Embedded
    val materialCategory: MaterialCategory,
    @Relation(
        parentColumn = "materialCategoryId",
        entityColumn = "materialCategoryId"
    )
    val materials: List<Material>
)
