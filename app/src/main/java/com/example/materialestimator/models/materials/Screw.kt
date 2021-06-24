package com.example.materialestimator.models.materials

import com.example.materialestimator.models.Material
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class Screw(

    name: String,
    unitprice: Double,
    coverage: Double,
    image: String,
    categoryid: Int

) : Material(
    subtype = "Screw",
    name = name,
    unitprice = unitprice,
    coverage = coverage,
    image = image,
    categoryid = categoryid
) {

    fun calcQty(length: Double, width: Double): Double {
        return length * width / this.coverage
    }

    override fun getOptionalProperties(): ArrayList<Pair<String, String>> {
        val array: ArrayList<Pair<String, String>> = arrayListOf()
        array.add(Pair("Name:", name))
        array.add(Pair("Unit Price:", unitprice.toString()))
        array.add(Pair("Coverage:", coverage.toString()))
        return array
    }

}
