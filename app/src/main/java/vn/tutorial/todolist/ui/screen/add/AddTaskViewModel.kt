package vn.tutorial.todolist.ui.screen.add

import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import vn.tutorial.todolist.data.repository.TaskRepository
import vn.tutorial.todolist.model.Task
import java.sql.Date
import java.time.LocalDateTime

data class TaskDetails(
    val id: Int = 0,

    val title: String = "",

    val userId: Int = 1,

    val categoryId: Int = 1,

    val description: String = "",

    val isCompleted: Boolean = false,

    val dateCreated: Date = Date(System.currentTimeMillis()),

    val dateBegin: LocalDateTime = LocalDateTime.now(),

    val dateEnd: LocalDateTime = LocalDateTime.now()
)

fun TaskDetails.toTask(): Task = Task (
    id = id,
    title = title,
    userId = userId,
    categoryId = categoryId,
    description = description,
    isCompleted = isCompleted,
    dateCreated = dateCreated,
    dateBegin = dateBegin,
    dateEnd = dateEnd
)

class AddTaskViewModel(
    val taskRepository: TaskRepository
) : ViewModel() {
    var taskUiState by mutableStateOf(TaskUiState())
        private set

    suspend fun saveTask(task: Task): Boolean {
        if(isValid()) {
            taskRepository.saveTask(taskUiState.taskDetails.toTask())
            return true
        }
        return false
    }

    private fun isValid(taskDetails: TaskDetails = taskUiState.taskDetails): Boolean {
        return with(taskDetails) {
            title.isNotBlank()
        }
    }

     fun updateUiState(taskDetails: TaskDetails) {
        taskUiState = TaskUiState(
            taskDetails = taskDetails,
            isValid = isValid(taskDetails)
        )
    }
}

data class TaskUiState(
    val taskDetails: TaskDetails = TaskDetails(),
    val isValid: Boolean = false
)


