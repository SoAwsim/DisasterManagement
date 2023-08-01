package com.cse344group10.disastermanagement.ui.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.cse344group10.disastermanagement.ui.screens.authentication.RegisterBuildingScreen
import com.cse344group10.disastermanagement.ui.screens.authentication.RegisterEmailPasswordScreen
import com.cse344group10.disastermanagement.ui.screens.authentication.RegisterPersonalScreen
import com.cse344group10.disastermanagement.ui.screens.authentication.RegisterQuestionScreen

enum class RegisterScreens {
    RegisterEmailPassword,
    RegisterPersonal,
    RegisterQuestion,
    RegisterMap
}

fun NavGraphBuilder.registerNavGraph(navController: NavController) {
    navigation(
        route = AuthenticationGraphs.Register.name,
        startDestination = RegisterScreens.RegisterEmailPassword.name
    ) {
        composable(route = RegisterScreens.RegisterEmailPassword.name) {
            RegisterEmailPasswordScreen(
                backButtonAction = {navController.navigateUp()},
                nextButtonAction = {mail, password -> navController.navigate(route = "${RegisterScreens.RegisterPersonal.name}/$mail/$password")}
            )
        }

        composable(
            route = "${RegisterScreens.RegisterPersonal.name}/{mail}/{password}",
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
                        route = RegisterScreens.RegisterQuestion.name +
                                "/$userMail/$userPassword/$userName/$userSurname" +
                                "/$userAge/$userGender/$userBloodType")
                }
            )
        }

        composable(
            route = "${RegisterScreens.RegisterQuestion.name}/{mail}/{password}/{name}/{surname}/{age}/{gender}/{blood}",
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
                        route = RegisterScreens.RegisterMap.name +
                                "/$userMail/$userPassword/$userName/$userSurname" +
                                "/$userAge/$userGender/$userBlood/$userQuestionType" +
                                "/$userQuestionAnswer/$userNotes"
                    )
                }
            )
        }

        composable(
            route = RegisterScreens.RegisterMap.name +
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
                nextPage = {
                    navController.navigate(AuthenticationGraphs.Login.name) {
                        popUpTo(
                            navController.graph.id
                        ) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}