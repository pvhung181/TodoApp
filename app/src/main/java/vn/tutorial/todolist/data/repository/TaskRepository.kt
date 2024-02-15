package vn.tutorial.todolist.data.repository

import kotlinx.coroutines.flow.Flow
import vn.tutorial.todolist.data.dao.TaskDao
import vn.tutorial.todolist.model.Category
import vn.tutorial.todolist.model.Task

interface TaskRepository {
    fun getTaskById(id: Int): Flow<Task>
    fun getTaskByCategoryId(categoryId: Int): Flow<List<Task>>
    fun getAllTask(): Flow<List<Task>>
    suspend fun saveTask(task: Task)

    suspend fun deleteTask  (task: Task)

}

class TaskRepositoryImpl(
    val taskDao: TaskDao
) : TaskRepository {
    override fun getTaskById(id: Int): Flow<Task> {
        return taskDao.getTaskById(id)
    }
    override fun getTaskByCategoryId(categoryId: Int): Flow<List<Task>> {
        return taskDao.getTaskByCategoryId(categoryId)
    }

    override fun getAllTask(): Flow<List<Task>> {
        return taskDao.getAllTask()
    }

    override suspend fun saveTask(task: Task) {
        taskDao.saveTask(task)
    }

    override suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }


}

