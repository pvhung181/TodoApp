package vn.tutorial.todolist.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import vn.tutorial.todolist.TodoApplication
import vn.tutorial.todolist.ui.screen.add.AddTaskViewModel
import vn.tutorial.todolist.ui.screen.home.HomeViewModel
import vn.tutorial.todolist.ui.screen.start.StartViewModel
import vn.tutorial.todolist.ui.screen.user.SettingScreenViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(
                taskRepository = todoApplication().container.taskRepository,
                categoryRepository = todoApplication().container.categoryRepository,
                userRepository = todoApplication().container.userRepository
            )
        }

        initializer {
            SettingScreenViewModel(
                dataStoreManager = todoApplication().container.dataStoreManager,
                userRepository = todoApplication().container.userRepository

            )
        }

        initializer {
            AddTaskViewModel(
                taskRepository = todoApplication().container.taskRepository,
                userRepository = todoApplication().container.userRepository,
                worManagerNotificationRepository = todoApplication().container.workManagerNotificationRepository
            )
        }

        initializer {
            StartViewModel(
                userRepository = todoApplication().container.userRepository,
                categoryRepository = todoApplication().container.categoryRepository,
                dataStoreManager = todoApplication().container.dataStoreManager,
                todoNotificationService = todoApplication().container.todoNotificationService,
                autoSyncManagerRepository = todoApplication().container.autoSyncManagerRepository
            )
        }
    }
}

fun CreationExtras.todoApplication(): TodoApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TodoApplication)