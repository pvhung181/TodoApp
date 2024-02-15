package vn.tutorial.todolist.data

import vn.tutorial.todolist.model.Category

fun getCategory(): List<Category> {
    return listOf<Category>(
        Category(1, "Personal"),
        Category(2, "Work"),
        Category(3, "Shopping")
    )
}