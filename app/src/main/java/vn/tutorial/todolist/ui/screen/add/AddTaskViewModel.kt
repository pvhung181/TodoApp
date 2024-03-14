package vn.tutorial.todolist.ui.screen.add

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import vn.tutorial.todolist.data.repository.TaskRepository
import vn.tutorial.todolist.data.repository.UserRepository
import vn.tutorial.todolist.data.repository.WorManagerNotificationRepository
import vn.tutorial.todolist.model.Task
import vn.tutorial.todolist.model.User
import java.sql.Date
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

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

fun TaskDetails.toTask(): Task = Task(
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
    val taskRepository: TaskRepository,
    val userRepository: UserRepository,
    private val worManagerNotificationRepository: WorManagerNotificationRepository
) : ViewModel() {
    var taskUiState by mutableStateOf(TaskUiState())
        private set

    val user = userRepository.getUser(1)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), User(
            1, "username", "", "", Date.valueOf(LocalDate.now().minusDays(2).toString()), 0,0,0
        ))

    suspend fun saveTask(task: Task): Boolean {
        if (isValid()) {
            taskRepository.saveTask(taskUiState.taskDetails.toTask())
            return true
        }
        return false
    }

    fun addNotification(content: String, start: LocalDateTime) {
        worManagerNotificationRepository.setWorkRequest(content, start)
    }

    fun isValid(taskDetails: TaskDetails = taskUiState.taskDetails): Boolean {
        return with(taskDetails) {
            title.isNotBlank()
        }
    }

    fun updateUiState(taskDetails: TaskDetails) {
        taskUiState = TaskUiState(
            taskDetails = taskDetails, isValid = isValid(taskDetails)
        )
    }

    suspend fun updateUser(user: User) {
        userRepository.update(user)
    }

    fun validateDate(
        startDate: LocalDate, endDate: LocalDate, startTime: LocalTime, endTime: LocalTime
    ): Boolean {
        val dateTimeNow = LocalDateTime.of(LocalDate.now(), LocalTime.now())
        val start = LocalDateTime.of(startDate, startTime)
        val end = LocalDateTime.of(endDate, endTime)

        if (start.isAfter(end)) return false
        return !(start.isBefore(dateTimeNow) || end.isBefore(dateTimeNow))
    }
}

data class TaskUiState(
    val taskDetails: TaskDetails = TaskDetails(), val isValid: Boolean = false
)


