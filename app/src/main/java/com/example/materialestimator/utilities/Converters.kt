package com.example.materialestimator.utilities

import androidx.room.TypeConverter
import com.example.materialestimator.models.entities.Material
import com.example.materialestimator.models.materials.*
import com.squareup.moshi.FromJson
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import com.squareup.moshi.Types
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.text.SimpleDateFormat
import java.util.*

/**
 * Type converters for Room.
 * @TypeConverter is used by Room.
 * @JvmStatic Will throw an InvocationTargetException without it. Its needed by java to changes
 * MaterialTypeConverter.Companion().toJson() to MaterialTypeConverter.toJson()
 */

class Converters {

    companion object {

        fun DateToDDMMMYYYY(date: Date): String? {
            val df = SimpleDateFormat("dd MMM yyyy", Locale.US)
            return df.format(date)
        }

        @TypeConverter
        @JvmStatic
        fun fromTimestamp(value: Long?): Date? {
            return value?.let { Date(it) }
        }

        @TypeConverter
        @JvmStatic
        fun dateToTimestamp(date: Date?): Long? {
            return date?.time
        }


    }
}