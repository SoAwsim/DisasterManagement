package com.cse344group10.disastermanagement.ui.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.cse344group10.disastermanagement.ui.navigation.UIGraphs
import com.cse344group10.disastermanagement.ui.screens.authentication.RegisterBuildingScreen
import com.cse344group10.disastermanagement.ui.screens.authentication.RegisterEmailPasswordScreen
import com.cse344group10.disastermanagement.ui.screens.authentication.RegisterPersonalScreen
import com.cse344group10.disastermanagement.ui.screens.authentication.RegisterQuestionScreen
import com.cse344group10.disastermanagement.ui.screens.home.AboutScreen
import com.cse344group10.disastermanagement.ui.screens.home.AdminScreen
import com.cse344group10.disastermanagement.ui.screens.home.EditUsersScreen
import com.cse344group10.disastermanagement.ui.screens.home.HomeScreen
import com.cse344group10.disastermanagement.ui.screens.home.ProfileScreen
import com.cse344group10.disastermanagement.ui.screens.home.RequestSuppliesScreen
import com.cse344group10.disastermanagement.ui.screens.home.ViewDetailedBuildingInfoScreen

enum class HomeScreens{
    Home,
    About,
    Profile,
    DetailedBuilding,
    AdminPanel,
    AdminUsers,
    EmployeeRegisterEmailPassword,
    EmployeeRegisterPersonal,
    EmployeeRegisterQuestion,
    EmployeeRegisterMap,
    MakeSupplyRequest
}

fun NavGraphBuilder.homeGraph(navController: NavController) {
    navigation(
        route = UIGraphs.HomeRoot.name,
        startDestination = HomeScreens.Home.name
    ) {
        composable(route = HomeScreens.Home.name) {
            HomeScreen(
                signOut = {
                    navController.navigate(route = UIGraphs.Authentication.name) {
                        popUpTo(route = UIGraphs.HomeRoot.name) {
                            inclusive = true
                        }
                    }
                },
                aboutAction = {
                    navController.navigate(route = HomeScreens.About.name)
                },
                profileAction = {
                    navController.navigate(route = HomeScreens.Profile.name)
                },
                adminAction = {
                    navController.navigate(route =  HomeScreens.AdminPanel.name)
                },
                viewDetailedBuildingAction = {
                    navController.navigate(route = HomeScreens.DetailedBuilding.name)
                },
                requestSupplyAction = {
                    navController.navigate(route = HomeScreens.MakeSupplyRequest.name)
                }
            )
        }

        composable(route = HomeScreens.About.name) {
            AboutScreen(
                backAction = {
                    navController.navigateUp()
                }
            )
        }

        composable(route = HomeScreens.Profile.name) {
            ProfileScreen(
                backAction = {
                    navController.navigateUp()
                }
            )
        }

        composable(route = HomeScreens.DetailedBuilding.name) {
            ViewDetailedBuildingInfoScreen(
                backAction = {
                    navController.navigateUp()
                }
            )
        }

        composable(route = HomeScreens.AdminPanel.name) {
            AdminScreen(
                editUsers = {
                    navController.navigate(route = HomeScreens.AdminUsers.name)
                },
                backAction = {
                    navController.navigateUp()
                }
            )
        }

        composable(route = HomeScreens.AdminUsers.name) {
            EditUsersScreen(
                backAction = {
                    navController.navigateUp()
                },
                nextPage = { navController.navigate(route = HomeScreens.EmployeeRegisterEmailPassword.name ) }
            )
        }

        composable(route = HomeScreens.MakeSupplyRequest.name) {
            RequestSuppliesScreen(
                backAction = {
                    navController.navigateUp()
                }
            )
        }

        composable(route = HomeScreens.EmployeeRegisterEmailPassword.name) {
            RegisterEmailPasswordScreen(
                backButtonAction = { navController.navigateUp() },
                nextButtonAction = {mail, password ->
                    navController.navigate(route = "${HomeScreens.EmployeeRegisterPersonal.name}/$mail/$password"
                    )}
            )
        }

        composable(
            route = "${HomeScreens.EmployeeRegisterPersonal.name}/{mail}/{password}",
            arguments = listOf(
                navArgument("mail") { type = NavType.StringType },
                navArgument("password") { type = NavType.StringType }
            )
        ) {
            val mail = it.arguments?.getString("mail")!!
            val password = it.arguments?.getString("password")!!
            RegisterPersonalScreen(
                mail = mail,
                password = password,
                backButtonAction = { navController.navigateUp() },
                nextButtonAction = {userMail, userPassword, userName, userSurname, userAge, userGender, userBloodType ->
                    navController.navigate(
                        route = HomeScreens.EmployeeRegisterQuestion.name +
                                "/$userMail/$userPassword/$userName/$userSurname" +
                                "/$userAge/$userGender/$userBloodType")
                }
            )
        }

        composable(
            route = "${HomeScreens.EmployeeRegisterQuestion.name}/{mail}/{password}/{name}/{surname}/{age}/{gender}/{blood}",
            arguments = listOf(
                navArgument("mail") { type = NavType.StringType },
                navArgument("password") { type = NavType.StringType },
                navArgument("name") { type = NavType.StringType },
                navArgument("surname") { type = NavType.StringType },
                navArgument("age") { type = NavType.IntType },
                navArgument("gender") { type = NavType.IntType },
                navArgument("blood") { type = NavType.IntType }
            )
        ) {
            val mail = it.arguments?.getString("mail")!!
            val password = it.arguments?.getString("password")!!
            val name = it.arguments?.getString("name")!!
            val surname = it.arguments?.getString("surname")!!
            val age = it.arguments?.getInt("age")!!
            val gender = it.arguments?.getInt("gender")!!
            val blood = it.arguments?.getInt("blood")!!
            RegisterQuestionScreen(
                mail = mail,
                password = password,
                name = name,
                surname = surname,
                age = age,
                gender = gender,
                blood = blood,
                backButtonAction = { navController.navigateUp() },
                nextButtonAction = {
                        userMail,
                        userPassword,
                        userName,
                        userSurname,
                        userAge,
                        userGender,
                        userBlood,
                        userQuestionType,
                        userQuestionAnswer,
                        userNotes ->
                    navController.navigate(
                        route = HomeScreens.EmployeeRegisterMap.name +
                                "/$userMail/$userPassword/$userName/$userSurname" +
                                "/$userAge/$userGender/$userBlood/$userQuestionType" +
                                "/$userQuestionAnswer/$userNotes"
                    )
                }
            )
        }

        composable(
            route = HomeScreens.EmployeeRegisterMap.name +
                    "/{mail}/{password}/{name}/{surname}" +
                    "/{age}/{gender}/{blood}/{question}/{answer}/{notes}",
            arguments = listOf(
                navArgument("mail") { type = NavType.StringType },
                navArgument("password") { type = NavType.StringType },
                navArgument("name") { type = NavType.StringType },
                navArgument("surname") { type = NavType.StringType },
                navArgument("age") { type = NavType.IntType },
                navArgument("gender") { type = NavType.IntType },
                navArgument("blood") { type = NavType.IntType },
                navArgument("question") { type = NavType.IntType },
                navArgument("answer") { type = NavType.StringType },
                navArgument("notes") {type = NavType.StringType }
            )
        ) {
            val mail = it.arguments?.getString("mail")!!
            val password = it.arguments?.getString("password")!!
            val name = it.arguments?.getString("name")!!
            val surname = it.arguments?.getString("surname")!!
            val age = it.arguments?.getInt("age")!!
            val gender = it.arguments?.getInt("gender")!!
            val blood = it.arguments?.getInt("blood")!!
            val question = it.arguments?.getInt("question")!!
            val answer = it.arguments?.getString("answer")!!
            val notes = it.arguments?.getString("notes")!!
            RegisterBuildingScreen(
                mail = mail,
                password = password,
                name = name,
                surname = surname,
                age = age,
                gender = gender,
                blood = blood,
                question = question,
                answer = answer,
                notes = notes,
                chooseRole = true,
                nextPage = {
                    navController.popBackStack(route = HomeScreens.AdminUsers.name, inclusive = false)
                }
            )
        }
    }
}