package com.cse344group10.disastermanagement.ui.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.cse344group10.disastermanagement.ui.navigation.UIGraphs
import com.cse344group10.disastermanagement.ui.screens.loading.MapLoadingScreen
import com.cse344group10.disastermanagement.ui.screens.loading.UserLoadingScreen
import com.cse344group10.disastermanagement.ui.screens.loading.WelcomeScreen

enum class MapCreateGraphs {
    Welcome,
    Loading,
    UserCreate
}

fun NavGraphBuilder.mapCreateGraph(navController: NavController) {
    navigation(
        route = UIGraphs.MapCreate.name,
        startDestination = MapCreateGraphs.Welcome.name
    ) {
        composable(route = MapCreateGraphs.Welcome.name) {
            WelcomeScreen {
                navController.navigate(MapCreateGraphs.Loading.name) {
                    popUpTo(MapCreateGraphs.Welcome.name) {
                        inclusive = true
                    }
                }
            }
        }

        composable(route = MapCreateGraphs.Loading.name) {
            MapLoadingScreen {
                navController.navigate(MapCreateGraphs.UserCreate.name) {
                    popUpTo(MapCreateGraphs.Loading.name) {
                        inclusive = true
                    }
                }
            }
        }

        composable(route = MapCreateGraphs.UserCreate.name) {
            UserLoadingScreen {
                navController.navigate(UIGraphs.Authentication.name) {
                    popUpTo(MapCreateGraphs.UserCreate.name) {
                        inclusive = true
                    }
                }
            }
        }
    }
}