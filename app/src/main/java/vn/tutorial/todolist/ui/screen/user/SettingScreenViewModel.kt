package vn.tutorial.todolist.ui.screen.user

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import vn.tutorial.todolist.data.DataStoreManager

class SettingScreenViewModel(
    val dataStoreManager: DataStoreManager
) : ViewModel() {

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