package vn.tutorial.todolist.ui.screen.user

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
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), User(
            1, "username", "", Date.valueOf(LocalDate.now().minusDays(2).toString()), 0,0,0
        )
        )

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

}


data class SettingUiState (
    var isDarkTheme: Boolean = false
)