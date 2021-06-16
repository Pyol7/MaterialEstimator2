package com.example.materialestimator.models.finishes

import com.example.materialestimator.models.entities.Material
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class JointCompound(

    name: String,
    unitprice: Double,
    coverage: Double,
    image: String,
    materialCategoryID: Int

) : Material(
    subtype = "JointCompound",
    name = name,
    unitprice = unitprice,
    coverage = coverage,
    image = image,
    materialCategoryID = materialCategoryID
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
