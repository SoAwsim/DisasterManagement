package com.cse344group10.disastermanagement.ui.navigation.graphs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cse344group10.disastermanagement.ui.screens.home.BuildingInfoScreen
import com.cse344group10.disastermanagement.ui.screens.home.HomeScreenLayout

enum class BottomNavigationScreens(val title: String, val imageVector: ImageVector) {
    Home("Home", Icons.Default.Home),
    BuildingInfo("Building Info", Icons.Default.Warning)
}

@Composable
fun BottomBarGraph(
    navController: NavHostController,
    viewDetailedBuildingAction: () -> Unit = {},
    requestSupplyAction: () -> Unit = {},
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavigationScreens.Home.name
    ) {
        composable(route = BottomNavigationScreens.Home.name) {
            HomeScreenLayout(
                requestSupplyAction = requestSupplyAction,
                modifier = modifier
            )
        }

        composable(route = BottomNavigationScreens.BuildingInfo.name) {
            BuildingInfoScreen(
                modifier = modifier,
                navigateDetailedBuilding = {
                    viewDetailedBuildingAction()
                }
            )
        }
    }
}