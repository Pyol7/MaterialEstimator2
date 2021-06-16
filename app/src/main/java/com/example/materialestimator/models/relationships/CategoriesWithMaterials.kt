package com.example.materialestimator.models.relationships

import androidx.room.Embedded
import androidx.room.Relation
import com.example.materialestimator.models.entities.Category
import com.example.materialestimator.models.entities.Material

data class CategoriesWithMaterials(
    @Embedded
    val category: Category,
    @Relation(
        parentColumn = "categoryID",
        entityColumn = "materialCategoryID"
    )
    val materials: List<Material>
)
