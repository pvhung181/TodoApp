package vn.tutorial.todolist.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import vn.tutorial.todolist.ui.screen.add.AddScreen
import vn.tutorial.todolist.ui.screen.home.HomeScreen
import vn.tutorial.todolist.ui.screen.user.SettingScreen
import vn.tutorial.todolist.ui.screen.user.UserScreen

@Composable
fun TodoNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = HomeScreen.route
    ) {
        composable(HomeScreen.route) {
            HomeScreen(
                navigateToHome = {
                    navController.popBackStack(route = HomeScreen.route, inclusive = false)
                },
                navigateToAdd = {
                    navController.navigate(AddScreen.route)
                },
                navigateToSetting = {
                    navController.navigate(SettingScreen.route)
                }
            )
        }

        composable(AddScreen.route) {
            AddScreen(
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }

        composable(SettingScreen.route) {
            UserScreen(
                navigateToHome = {
                    navController.popBackStack(route = HomeScreen.route, inclusive = false)
                },
                navigateToAdd = {
                    navController.navigate(AddScreen.route)
                },
                navigateToSetting = {
                    navController.navigate(SettingScreen.route)
                }
            )
        }
    }
}
