package com.example.materialestimator.utilities

import android.util.Log
import androidx.room.TypeConverter
import com.example.materialestimator.TAG
import com.example.materialestimator.models.entities.Project
import java.text.DateFormat
import java.util.*

/**
 * Type converters for Room.
 * @TypeConverter is used by Room.
 * @ToJson is used by Moshi.
 * @JvmStatic Will throw an InvocationTargetException without it. Its needed by java to changes
 * MaterialTypeConverter.Companion().toJson() to MaterialTypeConverter.toJson()
 */

class Converters {

    companion object {

        fun dateToCalendar(date: Date): Calendar {
            val calendar = Calendar.getInstance()
            calendar.time = date
            return calendar
        }

        fun calendarToDate(calendar: Calendar): Date {
            return calendar.time
        }

        /**
         * getDateInstance() defaults to Apr 06, 1974 format and uses the phones locale setting.
         * See https://developer.android.com/reference/java/text/DateFormat
         * for other available string formatting methods
         */
        fun dateToString(date: Date): String {
            // format date based on the phones locale setting
            return DateFormat.getDateInstance().format(date)
        }

        fun yearMonthDayToString(year: Int, month: Int, day: Int): String {
            // Create Calender
            val calendar = Calendar.getInstance()
            calendar.set(year, month, day)
            // convert Calender to Date
            val date = calendar.time
            // format date based on the phones locale setting
            return DateFormat.getDateInstance().format(date)
        }

        fun stringToDate(string: String?): Date? {
            var date: Date? = null
                if (string?.isNotEmpty() == true) {
                    try {
                        date = DateFormat.getDateInstance().parse(string)
                    } catch (e: Exception) {
                        Log.i(TAG, e.toString())
                    }
                } else {
                    return null
                }
            return date
        }

        fun stringToInteger(string: String?): Int? {
            return if (string?.isNotEmpty() == true) {
                Integer.parseInt(string)
            } else {
                null
            }
        }

        @TypeConverter
        @JvmStatic
        fun dateToTimestamp(date: Date?): Long? {
            return date?.time
        }

        @TypeConverter
        @JvmStatic
        fun timestampToDate(timeStamp: Long?): Date? {
            return timeStamp?.let { Date(it) }
        }

    }
}