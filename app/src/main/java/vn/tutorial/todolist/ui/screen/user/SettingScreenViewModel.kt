package vn.tutorial.todolist.ui.screen.user

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import vn.tutorial.todolist.data.DataStoreManager
import vn.tutorial.todolist.data.repository.UserRepository
import vn.tutorial.todolist.model.User
import java.sql.Date
import java.time.LocalDate

class SettingScreenViewModel(
    val dataStoreManager: DataStoreManager,
    val userRepository: UserRepository
) : ViewModel() {

     val user = userRepository.getUser(1)
        .stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000), User(
                1, "username", "","" ,Date.valueOf(LocalDate.now().minusDays(2).toString()), 0, 0, 0
            )
        )


    private val _userInfo = mutableStateOf(User( 1, "username", "", "", Date.valueOf(LocalDate.now().minusDays(2).toString()), 0, 0, 0))
    val userInfo: State<User> = _userInfo
    var state = mutableStateOf(ProfileState.LOADING)

    init {
        Log.e("Test", "In init method in setting screen viewmodel")
        viewModelScope.launch {
            _userInfo.value = userRepository.getUserNotTracking(1)
        }

        state.value = ProfileState.FULFILL
    }


    val uiState: StateFlow<SettingUiState> =
        dataStoreManager.isDarkTheme
            .map {
                SettingUiState(
                    isDarkTheme = it
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = SettingUiState()
            )


    fun selectTheme(isDarkTheme: Boolean) {
        viewModelScope.launch {
            dataStoreManager.saveDarkThemePreferences(isDarkTheme = isDarkTheme)
        }
    }

    suspend fun updateUser(user: User) {
        userRepository.update(user)
    }

    fun updateUserInfor(user: User) {
        _userInfo.value = user
    }
}

enum class ProfileState{
    LOADING, FULFILL
}


data class SettingUiState(
    var isDarkTheme: Boolean = false
)