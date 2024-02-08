package vn.tutorial.todolist.uitilities

import java.time.LocalDateTime

fun prettierLocalDateTime(date: LocalDateTime): String {
    return "${date.year}-${date.monthValue}-${date.dayOfMonth} : ${date.hour}h${date.minute}'"
}