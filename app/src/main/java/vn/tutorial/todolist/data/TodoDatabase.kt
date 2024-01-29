package vn.tutorial.todolist.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import vn.tutorial.todolist.data.dao.TaskDao
import vn.tutorial.todolist.model.Category
import vn.tutorial.todolist.model.Task
import vn.tutorial.todolist.model.User

@Database(entities = [Category::class, Task::class, User::class],
    version = 1,
    exportSchema = false
)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun getTaskDao(): TaskDao


    companion object {
        @Volatile
        private var Instance: TodoDatabase ?= null

        fun getDatabase(context: Context): TodoDatabase =
            Instance ?: synchronized(this) {
                Room.databaseBuilder(context = context, klass = TodoDatabase::class.java, name = "todo_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also {
                        Instance = it
                    }
            }
    }
}