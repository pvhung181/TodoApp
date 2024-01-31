package vn.tutorial.todolist.ui

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key.Companion.Home
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import vn.tutorial.todolist.ui.navigation.TodoNavHost
import vn.tutorial.todolist.ui.screen.home.BottomAppBar
import vn.tutorial.todolist.ui.screen.home.HomeScreen

@Composable
fun TodoApp(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    TodoNavHost(navController = navController)
}

@Composable
@Preview
fun TodoAppPreview(

) {
    TodoApp()
}