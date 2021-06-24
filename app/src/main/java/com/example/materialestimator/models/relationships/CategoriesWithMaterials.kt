package com.example.materialestimator.models.relationships

import androidx.room.Embedded
import androidx.room.Relation
import com.example.materialestimator.models.Category
import com.example.materialestimator.models.Material

data class CategoriesWithMaterials(
    @Embedded
    val category: Category,
    @Relation(
        parentColumn = "id",
        entityColumn = "categoryid"
    )
    val materials: List<Material>
)
