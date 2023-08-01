package com.cse344group10.disastermanagement.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cse344group10.disastermanagement.database.datastore.SessionTokenDataStore
import com.cse344group10.disastermanagement.database.datastore.SystemStateDataStore
import com.cse344group10.disastermanagement.database.repository.DbRepo
import com.cse344group10.disastermanagement.models.token.SessionToken
import com.cse344group10.disastermanagement.ui.navigation.graphs.authNavGraph
import com.cse344group10.disastermanagement.ui.navigation.graphs.homeGraph
import com.cse344group10.disastermanagement.ui.navigation.graphs.mapCreateGraph

enum class UIGraphs {
    Root,
    Authentication,
    HomeRoot,
    MapCreate,
    Empty
}

@Composable
fun MainNavigationController() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        route = UIGraphs.Root.name,
        startDestination = UIGraphs.Empty.name
    ) {
        authNavGraph(navController =  navController)

        homeGraph(navController = navController)

        mapCreateGraph(navController = navController)

        composable(route = UIGraphs.Empty.name) {
            EmptyPage(
                navigate = {
                    navController.navigate(route = it) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}

@Composable
private fun EmptyPage(
    navigate: (String) -> Unit = {}
) {
    val repo = DbRepo(LocalContext.current)
    val sessionStore = SessionTokenDataStore(LocalContext.current)

    LaunchedEffect(key1 = true) {
        // database should have 225 buildings if not then it is corrupted or
        // app is launched for the first time, either way database has to be generated again
        if (repo.getBuildingCount() != 225) {
            navigate(UIGraphs.MapCreate.name)
        } else {
            sessionStore.signedUserFlow.collect {
                when (it) {
                    0 -> navigate(UIGraphs.Authentication.name)
                    else -> {
                        SessionToken.initToken(repo.getUserById(it)!!)
                        navigate(UIGraphs.HomeRoot.name)
                    }
                }
            }
        }
    }
}