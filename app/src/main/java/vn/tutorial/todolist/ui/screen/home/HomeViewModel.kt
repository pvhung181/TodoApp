package vn.tutorial.todolist.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import vn.tutorial.todolist.data.repository.CategoryRepository
import vn.tutorial.todolist.data.repository.TaskRepository
import vn.tutorial.todolist.model.Category
import vn.tutorial.todolist.model.Task

class HomeViewModel(
    val taskRepository: TaskRepository,
    val categoryRepository: CategoryRepository
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

    init {
        viewModelScope.launch {
            var categories: List<Category> = categoryRepository.getALlCategoriesStream()
        }
    }

}

class HomeUiState(
    val tasks: List<Task> = listOf(),
)

fun HomeUiState.getByCategoryId(id: Int): List<Task> {
    return tasks.filter {
        it.categoryId == id
    }
}