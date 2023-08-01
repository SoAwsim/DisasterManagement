package com.cse344group10.disastermanagement.ui.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.cse344group10.disastermanagement.ui.navigation.UIGraphs
import com.cse344group10.disastermanagement.ui.screens.authentication.LoginScreen

enum class AuthenticationGraphs {
    Login,
    Register
}

fun NavGraphBuilder.authNavGraph(navController: NavController) {
    navigation(
        route = UIGraphs.Authentication.name,
        startDestination = AuthenticationGraphs.Login.name
    ) {
        composable(route = AuthenticationGraphs.Login.name) {
            LoginScreen(
                createAccount = { navController.navigate(AuthenticationGraphs.Register.name) },
                signInAction = {
                    navController.navigate(UIGraphs.HomeRoot.name) {
                        popUpTo(AuthenticationGraphs.Login.name) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        registerNavGraph(navController = navController)
    }
}