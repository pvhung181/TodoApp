package vn.tutorial.todolist.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "full_name")
    val fullName: String,

    @ColumnInfo(name = "avatar")
    val avatar: String,

    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "birth_day")
    val birthDay: java.sql.Date,

    @ColumnInfo(name = "coming_tasks")
    val comingTasks: Int,

    @ColumnInfo(name = "completed_tasks")
    val completedTasks: Int,

    @ColumnInfo(name = "total_tasks")
    val totalTasks: Int
)
