package com.example.materialestimator.models.finishes

import com.example.materialestimator.models.entities.Material
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class Tee(
    name: String,
    unitprice: Double,
    length: Double,
    image: String,
    materialCategoryID: Int

) : Material(
    subtype = "Tee",
    name = name,
    unitprice = unitprice,
    length = length,
    image = image,
    materialCategoryID = materialCategoryID
) {

    fun calcQty(length: Double, width: Double): Double {
        return length * width / this.length
    }

    override fun getOptionalProperties(): ArrayList<Pair<String, String>> {
        val array: ArrayList<Pair<String, String>> = arrayListOf()
        array.add(Pair("Name:", name))
        array.add(Pair("Unit Price:", unitprice.toString()))
        array.add(Pair("Length:", length.toString()))
        return array
    }

}
