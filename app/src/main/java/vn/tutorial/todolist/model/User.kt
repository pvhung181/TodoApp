package vn.tutorial.todolist.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date


@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private val id: Int,

    @ColumnInfo(name = "full_name")
    private val fullName: String,

    @ColumnInfo(name = "avatar")
    private val avatar: String,

    @ColumnInfo(name = "birth_day")
    private val birthDay: Date,

    @ColumnInfo(name = "coming_tasks")
    private val comingTasks: Int,

    @ColumnInfo(name = "completed_tasks")
    private val completedTasks: Int,

    @ColumnInfo(name = "total_tasks")
    private val totalTasks: Int
)
