package vn.tutorial.todolist

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.flow.map
import vn.tutorial.todolist.data.DataStoreManager
import vn.tutorial.todolist.data.repository.UserPreferencesRepository
import vn.tutorial.todolist.ui.TodoApp
import vn.tutorial.todolist.ui.theme.TodoListTheme
import java.io.File
import java.io.FileNotFoundException

class MainActivity : ComponentActivity() {
    private lateinit var dataStoreManager: DataStoreManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataStoreManager = DataStoreManager(this)



        setContent {
            TodoListTheme(
                darkTheme = dataStoreManager.getValueDarkTheme()
            ) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TodoApp(
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }

}
