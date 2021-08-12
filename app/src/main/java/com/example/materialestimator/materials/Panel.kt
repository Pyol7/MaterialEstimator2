package com.example.materialestimator.materials

import com.example.materialestimator.storage.local.entities.Material
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class Panel(

    name: String,
    unitprice: Double,
    length: Double,
    width: Double,
    image: String,
    materialCategoryId: Long

) : Material(
    subtype = "Panel",
    name = name,
    unitprice = unitprice,
    length = length,
    width = width,
    image = image,
    materialCategoryId = materialCategoryId,
) {

    fun calcQty(length: Double, width: Double): Double {
        return length * width / (this.length * this.width)
    }

    override fun getOptionalProperties(): ArrayList<Pair<String, String>> {
        val array: ArrayList<Pair<String, String>> = arrayListOf()
        array.add(Pair("Name:", name))
        array.add(Pair("Unit Price:", unitprice.toString()))
        array.add(Pair("Length:", length.toString()))
        array.add(Pair("Width:", width.toString()))
        return array
    }

}
