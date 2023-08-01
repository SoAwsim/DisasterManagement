package com.cse344group10.disastermanagement.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cse344group10.disastermanagement.database.datastore.SystemStateDataStore
import com.cse344group10.disastermanagement.models.token.SessionToken
import com.cse344group10.disastermanagement.ui.viewmodels.home.AdminScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminScreen(
    editUsers: () -> Unit = {},
    backAction: () -> Unit = {}
){
    val systemStore = SystemStateDataStore(LocalContext.current)
    val systemState = systemStore.systemStateFlow.collectAsState(false)
    val viewModel: AdminScreenViewModel = viewModel()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Admin Panel") },
                navigationIcon = {
                    IconButton(onClick = backAction) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) {
        Column(
            modifier= Modifier.fillMaxSize().padding(it),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text="Greetings, admin ${SessionToken.currentlyLoggedUser!!.userName}",
                fontSize = 20.sp,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = if (systemState.value) "The system is now on emergency state" else "The system is in normal state",
                    fontSize=20.sp,
                    fontWeight = FontWeight.Bold,
                )

                Button(
                    onClick = { viewModel.clearEmergency(systemStore) },
                    enabled = systemState.value,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(all = 10.dp)
                ) {
                    Text(
                        text = "Clear Emergency",
                        fontSize = 22.sp
                    )
                }
            }

            Button(
                onClick = editUsers,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(all = 10.dp)
                    .size(70.dp)
            ) {
                Text(
                    text = "Edit Users",
                    fontSize = 22.sp
                )
            }
        }
    }

}


























@Preview(showSystemUi = true, showBackground = true)
@Composable
fun AdminScreenPreview() {
    AdminScreen()
}