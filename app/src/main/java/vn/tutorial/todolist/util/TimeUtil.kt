package vn.tutorial.todolist.util

import java.sql.Date
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Locale

fun prettierLocalDateTime(date: LocalDateTime): String {
    return "${date.year}-${date.monthValue}-${date.dayOfMonth} : ${date.hour}h${date.minute}'"
}

fun prettierTime(hour: String, minute: String): String {
    return "${if(hour.length == 1) hour.padStart(2, '0') else hour}:" +
            if(minute.length == 1) minute.padStart(2, '0') else minute
}

fun localDateTimeToDate(l: LocalDateTime): Date {
    return Date.valueOf(l.toLocalDate().toString())
}

fun miliToLocalDate(l: Long): LocalDate {
    return Instant.ofEpochMilli(l).atZone(ZoneId.systemDefault()).toLocalDate()
}

fun compareLocalDate(l1: LocalDateTime, l2: LocalDateTime) {
    l1.compareTo(l2)
}

val dateFormatter: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ROOT)