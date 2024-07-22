package com.example.todo

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

class DbConverter {
//    @TypeConverter
//    fun toDate(dateLong: Long?): Date? {
//        return dateLong?.let { Date(it) }
//    }
//
//    @TypeConverter
//    fun fromDate(date: Date?): Long? {
//        return if (date == null) null else date.getTime()
//    }
    @TypeConverter
    fun fromTimestamp(value: String?): LocalDateTime? {
        return value?.let {
            LocalDateTime.parse(it, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        }
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDateTime?): String? {
        return date?.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    }
}