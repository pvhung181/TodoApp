package vn.tutorial.todolist.util

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Locale

fun prettierLocalDateTime(date: LocalDateTime): String {
    return "${date.year}-${date.monthValue}-${date.dayOfMonth} : ${date.hour}h${date.minute}'"
}

fun prettierTime(hour: String, minute: String): String {
    return "${if(hour.length == 1) hour.padStart(2, '0') else hour}:" +
            if(minute.length == 1) minute.padStart(2, '0') else minute
}

val dateFormatter: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ROOT)