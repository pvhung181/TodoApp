package vn.tutorial.todolist.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(tableName = "task")
data class Task (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "task_id")
    private val id: Int,

    @ColumnInfo(name = "title")
    private val title: String,

    @ColumnInfo(name = "user_id")
    private val userId: Int,

    @ColumnInfo(name = "category_id")
    private val categoryId: Int,

    @ColumnInfo(name = "description")
    private val description: String,

    @ColumnInfo(name = "is_completed")
    private val isCompleted: Boolean,

    @ColumnInfo(name = "date_created")
    private val dateCreated: Date,

    @ColumnInfo(name = "date_begin")
    private val dateBegin: Date,

    @ColumnInfo(name = "date_end")
    private val dateEnd: Date,
)