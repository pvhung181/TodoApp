package vn.tutorial.todolist.data

import android.content.Context
import vn.tutorial.todolist.data.repository.AutoSyncManagerRepository
import vn.tutorial.todolist.data.repository.CategoryRepository
import vn.tutorial.todolist.data.repository.CategoryRepositoryImpl
import vn.tutorial.todolist.data.repository.TaskRepository
import vn.tutorial.todolist.data.repository.TaskRepositoryImpl
import vn.tutorial.todolist.data.repository.UserRepository
import vn.tutorial.todolist.data.repository.UserRepositoryImpl
import vn.tutorial.todolist.data.repository.WorManagerNotificationRepository
import vn.tutorial.todolist.service.NotificationService

interface Container {
    val taskRepository: TaskRepository
    val categoryRepository: CategoryRepository
    val dataStoreManager: DataStoreManager
    val userRepository: UserRepository
    val workManagerNotificationRepository: WorManagerNotificationRepository
    val autoSyncManagerRepository: AutoSyncManagerRepository
    val todoNotificationService: NotificationService
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
    override val userRepository: UserRepository by lazy {
        UserRepositoryImpl(
            userDao = TodoDatabase.getDatabase(context).getUserDao()
        )
    }
    override val workManagerNotificationRepository: WorManagerNotificationRepository
        get() = WorManagerNotificationRepository(context)
    override val autoSyncManagerRepository: AutoSyncManagerRepository
        get() = AutoSyncManagerRepository(context)
    override val todoNotificationService: NotificationService
        get() = NotificationService(context)

}
