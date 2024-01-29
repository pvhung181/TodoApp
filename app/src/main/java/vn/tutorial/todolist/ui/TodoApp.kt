package vn.tutorial.todolist.ui

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key.Companion.Home
import androidx.compose.ui.tooling.preview.Preview
import vn.tutorial.todolist.ui.screen.home.BottomAppBar
import vn.tutorial.todolist.ui.screen.home.HomeScreen

@Composable
fun TodoApp(
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {

        },
        bottomBar = {
            BottomAppBar()
        }
    ) {
        HomeScreen(
            paddingValue = it
        )
    }
}

@Composable
@Preview
fun TodoAppPreview(

) {
    TodoApp()
}