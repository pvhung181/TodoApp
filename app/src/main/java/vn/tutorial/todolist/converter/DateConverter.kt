package vn.tutorial.todolist.converter

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.sql.Date
import java.time.LocalDateTime

class DateConverter {
    @TypeConverter
    fun stringToDate(value: String?): Date? {
        return value?.let {
            Date.valueOf(value)
        }
    }

    @TypeConverter
    fun dateToString(date: Date?): String? {
        return date?.toString()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun stringToLocalDateTime(value: String): LocalDateTime {
        val date = value.split("T")[0].split("-")
        val time = value.split("T")[1].split(":")
        return LocalDateTime.of(date[0].toInt(), date[1].toInt(), date[2].toInt(), time[0].toInt(), time[1].toInt())
    }

    @TypeConverter
    fun localDateTimeToString(date: LocalDateTime): String {
        return date.toString()
    }


}