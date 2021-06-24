package com.example.materialestimator.models.materials

import com.example.materialestimator.models.Material
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class Steel(

    name: String,
    unitprice: Double,
    length: Double,
    image: String,
    categoryid: Int

) : Material(
    subtype = "Steel",
    name = name,
    unitprice = unitprice,
    length = length,
    image = image,
    categoryid = categoryid
) {

    fun calcQty(length: Double, width: Double): Double {
        return length * width / (this.length * this.width)
    }

    override fun getOptionalProperties(): ArrayList<Pair<String, String>> {
        val array: ArrayList<Pair<String, String>> = arrayListOf()
        array.add(Pair("Name:", name))
        array.add(Pair("Unit Price:", unitprice.toString()))
        array.add(Pair("Length:", length.toString()))
        return array
    }

}