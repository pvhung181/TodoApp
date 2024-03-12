package vn.tutorial.todolist

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import vn.tutorial.todolist.data.DataStoreManager
import vn.tutorial.todolist.ui.TodoApp
import vn.tutorial.todolist.ui.theme.TodoListTheme

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
                        isFirstTime = dataStoreManager.getIsFirstTime()
                    )
                }
            }
        }
    }
}
