package vn.tutorial.todolist.data

import android.content.Context
import vn.tutorial.todolist.data.repository.CategoryRepository
import vn.tutorial.todolist.data.repository.CategoryRepositoryImpl
import vn.tutorial.todolist.data.repository.TaskRepository
import vn.tutorial.todolist.data.repository.TaskRepositoryImpl

interface Container {
    val taskRepository: TaskRepository
    val categoryRepository: CategoryRepository
    val dataStoreManager: DataStoreManager
}

class DefaultContainer(
    val context: Context
) : Container {
    override val taskRepository: TaskRepository by lazy {
        TaskRepositoryImpl(
            taskDao = TodoDatabase.getDatabase(context).getTaskDao()
        )
    }

    override val categoryRepository: CategoryRepository by lazy {
        CategoryRepositoryImpl(
            categoryDao = TodoDatabase.getDatabase(context).getCategoryDao()
        )
    }
    override val dataStoreManager: DataStoreManager by lazy {
        DataStoreManager(context)
    }

}
