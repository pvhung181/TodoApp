package vn.tutorial.todolist.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import vn.tutorial.todolist.ui.navigation.TodoNavHost

@Composable
fun TodoApp(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    TodoNavHost(
        navController = navController
    )
}

@Composable
@Preview
fun TodoAppPreview(

) {
    TodoApp()
}