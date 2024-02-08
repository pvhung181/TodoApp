package vn.tutorial.todolist.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import vn.tutorial.todolist.R
import vn.tutorial.todolist.ui.screen.about.AboutScreen
import vn.tutorial.todolist.ui.screen.add.AddScreen
import vn.tutorial.todolist.ui.screen.home.CategoryTitle
import vn.tutorial.todolist.ui.screen.home.DetailTaskCategory
import vn.tutorial.todolist.ui.screen.home.DetailTaskCategoryScreen
import vn.tutorial.todolist.ui.screen.home.HomeScreen
import vn.tutorial.todolist.ui.screen.home.HomeViewModel
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
                },
                navigateToToday = {
                    navController.navigate("${DetailTaskCategory.root}/${CategoryTitle.TODAY.name}")
                },
                navigateToPlanned = {
                    navController.navigate("${DetailTaskCategory.root}/${CategoryTitle.PLANNED.name}")
                },
                navigateToPersonal = {
                    navController.navigate("${DetailTaskCategory.root}/${CategoryTitle.PERSONAL.name}")
                },
                navigateToWork = {
                    navController.navigate("${DetailTaskCategory.root}/${CategoryTitle.WORK.name}")
                },
                navigateToShopping = {
                    navController.navigate("${DetailTaskCategory.root}/${CategoryTitle.SHOPPING.name}")
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

        composable(AboutScreen.route) {
            AboutScreen(
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
                },
                navigateToAbout = {
                    navController.navigate(AboutScreen.route)
                }
            )
        }

        composable(
            DetailTaskCategory.route,
            arguments = listOf(navArgument("categoryTitle") { type = NavType.StringType })
        ) {
            DetailTaskCategoryScreen(
                navigateBack = {
                    navController.navigateUp()
                },
                title = it.arguments?.getString("categoryTitle") ?: "Error"
            )
        }

    }
}
