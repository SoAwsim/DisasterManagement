package com.cse344group10.disastermanagement.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.cse344group10.disastermanagement.database.datastore.SessionTokenDataStore
import com.cse344group10.disastermanagement.database.datastore.SystemStateDataStore
import com.cse344group10.disastermanagement.models.token.SessionToken
import com.cse344group10.disastermanagement.models.user.User
import com.cse344group10.disastermanagement.ui.navigation.graphs.BottomBarGraph
import com.cse344group10.disastermanagement.ui.navigation.graphs.BottomNavigationScreens
import com.cse344group10.disastermanagement.ui.uimodels.InfoCard
import com.cse344group10.disastermanagement.ui.viewmodels.home.HomeMainViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    signOut: () -> Unit = {},
    aboutAction: () -> Unit = {},
    profileAction: () -> Unit = {},
    adminAction: () -> Unit = {},
    requestSupplyAction: () -> Unit = {},
    viewDetailedBuildingAction: () -> Unit = {}
) {
    val viewModel: HomeMainViewModel = viewModel()
    val tokenStorage = SessionTokenDataStore(LocalContext.current)
    val systemStateStorage = SystemStateDataStore(LocalContext.current)
    val navController: NavHostController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = null,
                            modifier= Modifier.size(70.dp)
                        )
                        Text(text="${SessionToken.currentlyLoggedUser!!.userName} ${SessionToken.currentlyLoggedUser!!.userSurname.uppercase()}")
                    }
                }
                NavigationDrawerItem(
                    label = { Text(text = "Profile") },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            profileAction()
                        }
                    }
                )
                if (SessionToken.currentlyLoggedUser!!.userPermissionLevel == User.PermissionLevel.SystemAdministrator) {
                    NavigationDrawerItem(
                        label = { Text(text = "Admin Panel") },
                        selected = false,
                        onClick = {
                            scope.launch { drawerState.close() }
                            adminAction()
                        }
                    )
                }
                NavigationDrawerItem(
                    label = { Text(text = "About") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        aboutAction()
                    }
                )
                NavigationDrawerItem(
                    label = { Text(text = "Sign Out") },
                    selected = false,
                    onClick = {
                        signOut()
                        viewModel.signOut(tokenStorage)
                    }
                )
                NavigationDrawerItem(
                    label = { },
                    selected = false,
                    onClick = {
                        viewModel.changeSystemState(systemStateStorage)
                    }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { currentDestination?.route?.let { Text(text = if (it == "BuildingInfo") "Building Info" else "Home") } },
                    navigationIcon = { IconButton(onClick = { scope.launch { drawerState.open() } }) {
                        Icon(imageVector = Icons.Filled.Menu, contentDescription = null)
                    }}
                )
            },
            bottomBar = { BottomBar(navController = navController) }
        ) {
            BottomBarGraph(
                navController = navController,
                viewDetailedBuildingAction = viewDetailedBuildingAction,
                requestSupplyAction = requestSupplyAction,
                modifier = Modifier.padding(it)
            )
        }
    }
}

@Composable
private fun BottomBar(navController: NavHostController) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    NavigationBar {
        BottomNavigationScreens.values().forEach { screen ->
            val isItemSelected = currentDestination?.hierarchy?.any {
                it.route == screen.name
            } == true
            NavigationBarItem(
                label = { Text(text = screen.title) },
                icon = { Icon(imageVector = screen.imageVector, contentDescription = null) },
                selected = isItemSelected,
                onClick = {
                    if (!isItemSelected) {
                        navController.navigate(screen.name) {
                            popUpTo(navController.graph.findStartDestination().id)
                            launchSingleTop = true
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun HomeScreenLayout(
    modifier: Modifier = Modifier,
    requestSupplyAction: () -> Unit = {}
) {
    val stateDataStore = SystemStateDataStore(LocalContext.current)
    val systemState = stateDataStore.systemStateFlow.collectAsState(false)
    
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Hello ${SessionToken.currentlyLoggedUser!!.userName}! You can see the information about your building on this page.",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(10.dp)
        )
        Row(modifier = Modifier.padding(top = 28.dp, bottom = 28.dp)) {
            InfoCard(
                imageVector = Icons.Filled.Home,
                contentDescription = null,
                textContent = SessionToken.currentlyLoggedUser!!.userHouse.buildingYear.toString(),
                fontSize = 50.sp,
                iconSize = 70.dp,
                cardSize = 150.dp
            )

            InfoCard(
                imageVector = Icons.Filled.Person,
                contentDescription = null,
                textContent = SessionToken.currentlyLoggedUser!!.userHouse.residentCount.toString(),
                fontSize = 50.sp,
                iconSize = 70.dp,
                cardSize = 150.dp
            )
        }
        Spacer(modifier = Modifier.weight(1f))

        if (systemState.value) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "The system is currently in emergency mode!",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Button(
                        onClick = requestSupplyAction
                    ) {
                        Text(text = "Request Supplies")
                    }
                }
            }
        }
    }
}