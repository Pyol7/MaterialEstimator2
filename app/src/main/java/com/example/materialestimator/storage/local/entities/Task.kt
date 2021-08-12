package com.example.materialestimator.storage.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import java.util.*

/**
 * A task can belong to only one project and a project can have many tasks.
 */

@JsonClass(generateAdapter = true)
@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Project::class,
            parentColumns = ["projectId"],
            childColumns = ["projectId_fk"],
            onDelete = CASCADE
        )
    ],
    indices = [Index(value = ["projectId_fk"])]
)
class Task(
    @PrimaryKey(autoGenerate = true)
    var taskId: Long = 0,
    var projectId_fk: Long = 0,
    var title: String = "",
    var description: String = "",
    var startDate: Date? = null,
    var estimatedDays: Int? = 0,
    var estimatedHours: Int? = 0,
    var completionDate: Date? = null,
    var percentCompleted: Int? = 0,
    var materials: MutableList<Material>? = mutableListOf()
) {

    fun addMaterials(list: List<Material>?){
        list?.forEach {
            materials!!.add(it)
        }
    }

}
