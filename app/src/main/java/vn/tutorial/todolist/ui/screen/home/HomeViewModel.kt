package vn.tutorial.todolist.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import vn.tutorial.todolist.data.getDefaultUser
import vn.tutorial.todolist.data.repository.CategoryRepository
import vn.tutorial.todolist.data.repository.TaskRepository
import vn.tutorial.todolist.data.repository.UserRepository
import vn.tutorial.todolist.model.Category
import vn.tutorial.todolist.model.Task
import vn.tutorial.todolist.model.User
import java.sql.Date
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class HomeViewModel(
    val taskRepository: TaskRepository,
    val userRepository: UserRepository
) : ViewModel() {
    val allTasks: StateFlow<HomeUiState> =
        taskRepository.getAllTask().map { HomeUiState(it) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HomeUiState())

    val personalTasks: StateFlow<HomeUiState> =
        taskRepository.getTaskByCategoryId(1).map { HomeUiState(it) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HomeUiState())

    val workTasks: StateFlow<HomeUiState> =
        taskRepository.getTaskByCategoryId(2).map { HomeUiState(it) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HomeUiState())

    val shoppingTasks: StateFlow<HomeUiState> =
        taskRepository.getTaskByCategoryId(3).map { HomeUiState(it) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HomeUiState())

    val user = userRepository.getUser(1)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), getDefaultUser())

    suspend fun deleteTask(task: Task) {

            taskRepository.deleteTask(task)
    }

    suspend fun updateTask(task: Task) {
        taskRepository.updateTask(task)
    }
}

class HomeUiState(
    var tasks: List<Task> = listOf(),
)
