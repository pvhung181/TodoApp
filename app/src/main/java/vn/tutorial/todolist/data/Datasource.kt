package vn.tutorial.todolist.data

import vn.tutorial.todolist.model.Category
import vn.tutorial.todolist.model.User
import java.sql.Date
import java.time.LocalDate

fun getCategory(): List<Category> {
    return listOf<Category>(
        Category(1, "Personal"),
        Category(2, "Work"),
        Category(3, "Shopping")
    )
}

fun getDefaultUser(): User = User(
    id = 1,
    fullName = "username",
    avatar = "",
    email = "",
    birthDay =  Date.valueOf(LocalDate.now().minusYears(5).toString()),
    comingTasks = 0,
    completedTasks =  0,
    totalTasks = 0,
    address = "",
    city = ""

)
