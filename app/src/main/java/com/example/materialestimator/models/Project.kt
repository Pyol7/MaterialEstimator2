package com.example.materialestimator.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.collections.ArrayList

@Entity
class Project(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String = "",
    var image: String = "",
    var tasks: ArrayList<Task> = arrayListOf(),
    var crews: ArrayList<Crew> = arrayListOf(),
    var equipment: ArrayList<Equipment> = arrayListOf(),
    var photos: ArrayList<String> = arrayListOf(),
    var files: ArrayList<String> = arrayListOf(),
    var notes: ArrayList<String> = arrayListOf()

) {

}
