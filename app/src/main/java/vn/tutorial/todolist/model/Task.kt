package vn.tutorial.todolist.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDateTime


@Entity(
    tableName = "task",
    foreignKeys = [
        ForeignKey(entity = User::class, parentColumns = arrayOf("id"), childColumns = arrayOf("user_id"), onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = Category::class, parentColumns = arrayOf("category_id"), childColumns = arrayOf("category_id"), onDelete = ForeignKey.CASCADE)
    ]
)
data class Task(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "task_id")
    val id: Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "user_id")
    val userId: Int,

    @ColumnInfo(name = "category_id")
    val categoryId: Int,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "is_completed")
    var isCompleted: Boolean,

    @ColumnInfo(name = "date_created")
    val dateCreated: java.sql.Date,

    @ColumnInfo(name = "date_begin")
    val dateBegin: LocalDateTime,

    @ColumnInfo(name = "date_end")
    val dateEnd: LocalDateTime
)