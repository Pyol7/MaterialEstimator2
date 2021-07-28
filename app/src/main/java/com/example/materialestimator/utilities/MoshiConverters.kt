package com.example.materialestimator.utilities

import androidx.room.TypeConverter
import com.example.materialestimator.models.entities.Employee
import com.example.materialestimator.models.entities.Material
import com.example.materialestimator.models.entities.Project
import com.example.materialestimator.models.entities.Tool
import com.example.materialestimator.models.materials.*
import com.squareup.moshi.FromJson
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import com.squareup.moshi.Types
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.util.*

/**
 * Type converters for Moshi and Room.
 * @TypeConverter is used by Room.
 * @ToJson is used by Moshi.
 * @JvmStatic Will throw an InvocationTargetException without it. Its needed by java to changes
 * MaterialTypeConverter.Companion().toJson() to MaterialTypeConverter.toJson()
 */

class MoshiConverters {

    companion object {

        private var moshiSubtypes: Moshi = Moshi.Builder()
            .add(
                PolymorphicJsonAdapterFactory.of(Material::class.java, "subtype")
                    .withSubtype(CChannel::class.java, "CChannel")
                    .withSubtype(Furring::class.java, "Furring")
                    .withSubtype(JointCompound::class.java, "JointCompound")
                    .withSubtype(Panel::class.java, "Panel")
                    .withSubtype(Screw::class.java, "Screw")
                    .withSubtype(Tee::class.java, "Tee")
                    .withSubtype(WallAngle::class.java, "WallAngle")
                    .withSubtype(Lumber::class.java, "Lumber")
                    .withSubtype(Steel::class.java, "Steel")
            )
            .addLast(KotlinJsonAdapterFactory())
            .build()

        private val moshi = Moshi.Builder().add(Date::class.java, Rfc3339DateJsonAdapter()).build()

        @TypeConverter
        @ToJson
        @JvmStatic
        fun materialsToJson(materials: List<Material>): String {
            val type = Types.newParameterizedType(List::class.java, Material::class.java)
            val adapter = moshiSubtypes.adapter<List<Material>>(type)
            return adapter.toJson(materials)
        }

        @TypeConverter
        @FromJson
        @JvmStatic
        fun jsonToMaterials(json: String?): List<Material>? {
            val type = Types.newParameterizedType(List::class.java, Material::class.java)
            val adapter = moshiSubtypes.adapter<List<Material>>(type)
            return adapter.fromJson(json!!)
        }

        fun convertBaseTypeToSubtype(baseType: Material): Material? {
            return jsonToMaterial(materialToJson(baseType))
        }

        fun convertListOfBaseTypeToListOfSubtypes(baseTypeList: List<Material>): List<Material> {
            val subTypesList = arrayListOf<Material>()
            for (baseType in baseTypeList) {
                val subType = convertBaseTypeToSubtype(baseType)
                if (subType != null) {
                    subTypesList.add(subType)
                }
            }
            return subTypesList
        }

        @TypeConverter
        @ToJson
        @JvmStatic
        fun materialToJson(material: Material): String {
            val moshi = Moshi.Builder().build()
            val adapter = moshi.adapter(Material::class.java)
            return adapter.toJson(material)
        }

        @TypeConverter
        @FromJson
        @JvmStatic
        fun jsonToMaterial(json: String): Material? {
            val adapter = moshiSubtypes.adapter(Material::class.java)
            return adapter.fromJson(json)
        }

        @TypeConverter
        @ToJson
        @JvmStatic
        fun employeesToJson(employees: List<Employee>?): String {
            val type = Types.newParameterizedType(List::class.java, Employee::class.java)
            val adapter = moshi.adapter<List<Employee>>(type)
            return adapter.toJson(employees)
        }

        @TypeConverter
        @FromJson
        @JvmStatic
        fun jsonToEmployees(json: String?): List<Employee>? {
            val type = Types.newParameterizedType(List::class.java, Employee::class.java)
            val adapter = moshi.adapter<List<Employee>>(type)
            return adapter.fromJson(json!!)
        }

        @TypeConverter
        @ToJson
        @JvmStatic
        fun toolToJson(tool: Tool): String {
            val moshi = Moshi.Builder().build()
            val adapter = moshi.adapter(Tool::class.java)
            return adapter.toJson(tool)
        }

        @TypeConverter
        @FromJson
        @JvmStatic
        fun jsonToTool(json: String): Tool? {
            val adapter = moshiSubtypes.adapter(Tool::class.java)
            return adapter.fromJson(json)
        }

        @TypeConverter
        @ToJson
        @JvmStatic
        fun toolsToJson(tools: List<Tool>?): String {
            val type = Types.newParameterizedType(List::class.java, Tool::class.java)
            val adapter = moshi.adapter<List<Tool>>(type)
            return adapter.toJson(tools)
        }

        @TypeConverter
        @FromJson
        @JvmStatic
        fun jsonToTools(json: String?): List<Tool>? {
            val type = Types.newParameterizedType(List::class.java, Tool::class.java)
            val adapter = moshi.adapter<List<Tool>>(type)
            return adapter.fromJson(json!!)
        }

        @TypeConverter
        @ToJson
        @JvmStatic
        fun projectToJson(project: Project): String {
            val moshi = Moshi.Builder().build()
            val adapter = moshi.adapter(Project::class.java)
            return adapter.toJson(project)
        }

        @TypeConverter
        @FromJson
        @JvmStatic
        fun jsonToProject(json: String): Project? {
            val adapter = moshiSubtypes.adapter(Project::class.java)
            return adapter.fromJson(json)
        }






    }
}