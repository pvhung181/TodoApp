package vn.tutorial.todolist.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import vn.tutorial.todolist.ui.screen.about.AboutScreen
import vn.tutorial.todolist.ui.screen.add.AddScreen
import vn.tutorial.todolist.ui.screen.home.CategoryTitle
import vn.tutorial.todolist.ui.screen.home.DetailTaskCategory
import vn.tutorial.todolist.ui.screen.home.DetailTaskCategoryScreen
import vn.tutorial.todolist.ui.screen.home.HomeScreen
import vn.tutorial.todolist.ui.screen.start.CollectUserInfoScreen
import vn.tutorial.todolist.ui.screen.start.StartScreen
import vn.tutorial.todolist.ui.screen.start.UserCollectionScreen
import vn.tutorial.todolist.ui.screen.user.ProfileScreen
import vn.tutorial.todolist.ui.screen.user.SettingScreen
import vn.tutorial.todolist.ui.screen.user.UserScreen

@Composable
fun TodoNavHost(
    navController: NavHostController,
    isFirstTime: Boolean
) {
    NavHost(
        navController = navController,
        startDestination = if(isFirstTime) StartScreen.route else HomeScreen.route
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
                    navController.navigate("${DetailTaskCategory.root}/${CategoryTitle.TODAY.title}")
                },
                navigateToPlanned = {
                    navController.navigate("${DetailTaskCategory.root}/${CategoryTitle.PLANNED.title}")
                },
                navigateToPersonal = {
                    navController.navigate("${DetailTaskCategory.root}/${CategoryTitle.PERSONAL.title}")
                },
                navigateToWork = {
                    navController.navigate("${DetailTaskCategory.root}/${CategoryTitle.WORK.title}")
                },
                navigateToShopping = {
                    navController.navigate("${DetailTaskCategory.root}/${CategoryTitle.SHOPPING.title}")
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
                },
                navigateToProfile = {
                    navController.navigate(ProfileScreen.route)
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

        composable(UserCollectionScreen.route) {
            CollectUserInfoScreen(
                navigateToHome = {
                    navController.navigate(HomeScreen.route)
                },
                navigateUp = {
                    navController.navigateUp()
                }
            )
        }

        composable(ProfileScreen.route) {
            ProfileScreen(
                navigateUp = {
                    navController.navigateUp()
                }
            )
        }

        composable(StartScreen.route) {
            StartScreen (
                navigateToUserInfoCollection = {
                    navController.navigate(UserCollectionScreen.route)
                }
            )
        }


    }
}
