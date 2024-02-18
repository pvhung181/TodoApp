package vn.tutorial.todolist.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import vn.tutorial.todolist.model.Category
import vn.tutorial.todolist.model.Task

@Dao
interface TaskDao {

    @Query("select * from task where user_id = 1")
    fun getAllTask(): Flow<List<Task>>

    @Query("select * from task where task_id = :id")
    fun getTaskById(id: Int): Flow<Task>

    @Query("select * from task where category_id = :categoryId")
    fun getTaskByCategoryId(categoryId: Int): Flow<List<Task>>

    @Query("select * from task where date_begin like :date||'%' order by date_begin asc")
    suspend fun getTaskByDate(date: String) : List<Task>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

}