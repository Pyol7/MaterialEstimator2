package com.example.materialestimator.storage.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
@Entity
open class Material(

    @PrimaryKey(autoGenerate = true)
    var materialId: Long = 0,
    val subtype: String = "",
    open var name: String = "",
    open var unitprice: Double = 0.0,
    open var length: Double = 0.0,
    open var width: Double = 0.0,
    open var coverage: Double = 0.0,
    var qty: Int? = 0,
    var image: String = "",
    var selected: Boolean = false,
    var materialCategoryId: Long = 0
){

    open fun getOptionalProperties(): ArrayList<Pair<String, String>> {
        val array: ArrayList<Pair<String, String>> = arrayListOf()
        array.add(Pair("Name:", name))
        array.add(Pair("Unit Price:", unitprice.toString()))
        array.add(Pair("Length:", length.toString()))
        array.add(Pair("Width:", width.toString()))
        array.add(Pair("Coverage:", coverage.toString()))
        return array
    }

    override fun toString(): String {
        return "Material(name=$name, selected=$selected)"
    }

    override fun equals(other: Any?): Boolean {
        val obj = other as Material

        if (this.materialId != obj.materialId){
            return false
        }

        if (this.name != obj.name){
            return false
        }

        return true
    }

    operator fun component1() = materialId
    operator fun component2() = subtype
    operator fun component3() = name
    operator fun component4() = unitprice
    operator fun component5() = length
    operator fun component6() = width
    operator fun component7() = coverage
    operator fun component8() = image
    operator fun component9() = materialCategoryId
    operator fun component10() = selected

    override fun hashCode(): Int {
        var result = materialId.toInt()
        result = 31 * result + name.hashCode()
        return result
    }

}
