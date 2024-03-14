package vn.tutorial.todolist.ui.screen.start

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import vn.tutorial.todolist.data.DataStoreManager
import vn.tutorial.todolist.data.repository.AutoSyncManagerRepository
import vn.tutorial.todolist.data.repository.CategoryRepository
import vn.tutorial.todolist.data.repository.UserRepository
import vn.tutorial.todolist.model.Category
import vn.tutorial.todolist.model.User
import vn.tutorial.todolist.service.NotificationService
import vn.tutorial.todolist.util.miliToLocalDate
import java.sql.Date
import java.time.LocalDate

data class UserInfo(
    val userName: String = "",
    val avatar: String = "",
    val birthDay: LocalDate = LocalDate.now(),
    val comingTasks: Int = 0,
    val completedTasks: Int = 0,
    val totalTasks: Int = 0,
    val email: String = "",
    val address: String = "",
    val city: String = ""
)



class StartViewModel(
    val userRepository: UserRepository,
    val categoryRepository: CategoryRepository,
    val dataStoreManager: DataStoreManager,
    val todoNotificationService: NotificationService,
    val autoSyncManagerRepository: AutoSyncManagerRepository
) : ViewModel() {
    private var _uiState = mutableStateOf(StartUiState())
    val uiState = _uiState

    suspend fun insertCategories(ct: Category) {
        categoryRepository.insert(ct)
    }

    suspend fun insertUser(user: User) {
        userRepository.insert(user)
    }

    private fun isValid(userInfo: UserInfo): Boolean {
        return with(userInfo) {
            userName.isNotEmpty() && comingTasks == 0 && completedTasks == 0 &&
            totalTasks == 0 && LocalDate.now().year - birthDay.year >= 3
        }
    }

    fun updateVisitedUser() {
        viewModelScope.launch {
            dataStoreManager.saveIsFirstTime(false)
        }
    }

    fun updateUiState(userInfo: UserInfo) {
        _uiState.value = StartUiState(
            userInfo = userInfo,
            isValid = isValid(userInfo),
            currentCharacters = userInfo.userName.length
        )
    }
}

data class StartUiState(
    val userInfo: UserInfo = UserInfo(),
    val isValid: Boolean = false,
    val currentCharacters: Int = 0
)

fun UserInfo.toUser(): User = User(
    id = 1,
    fullName = userName,
    birthDay = Date.valueOf(birthDay.toString()),
    avatar = avatar,
    comingTasks = comingTasks,
    completedTasks = completedTasks,
    totalTasks = totalTasks,
    email = email
)
