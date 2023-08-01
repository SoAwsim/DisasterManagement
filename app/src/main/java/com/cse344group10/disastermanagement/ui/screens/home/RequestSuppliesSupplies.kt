package com.cse344group10.disastermanagement.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cse344group10.disastermanagement.database.repository.DbRepo
import com.cse344group10.disastermanagement.ui.viewmodels.home.RequestSuppliesScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestSuppliesScreen(
    backAction: () -> Unit = {}
) {
    val repo = DbRepo(LocalContext.current)

    val viewModel: RequestSuppliesScreenViewModel = viewModel()

    val loaded = viewModel.loaded.observeAsState(false)

    val showFoodAlertDialog = viewModel.showFoodAlertDialog.observeAsState(false)
    val foodAlertText = viewModel.foodAlertText.observeAsState("0")

    val showMedicalAlertDialog = viewModel.showMedicalAlertDialog.observeAsState(false)
    val medicalAlertText = viewModel.medicalAlertText.observeAsState("0")

    LaunchedEffect(key1 = true) {
        viewModel.getAllUserRequest(repo)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Request Supplies",
                        textAlign = TextAlign.Justify
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = backAction
                    ) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        },
        floatingActionButton = {
            if (loaded.value) {
                Row {
                    FloatingActionButton(onClick = { viewModel.updateShowFoodAlertDialog(true) }, modifier = Modifier.padding(10.dp)) {
                        Text(text = "Make Food Request", modifier = Modifier.padding(10.dp))

                    }
                    FloatingActionButton(onClick = { viewModel.updateShowMedicalAlertDialog(true) }, modifier = Modifier.padding(10.dp)) {
                        Text(text = "Make Medical Request", modifier = Modifier.padding(10.dp))
                    }
                }
            }
        }
    ) {
        if (showFoodAlertDialog.value) {
            AlertDialog(
                onDismissRequest = { viewModel.updateShowFoodAlertDialog(false) },
                title = { Text(text = "Make Food Request") },
                text = {
                       OutlinedTextField(
                           value = foodAlertText.value,
                           onValueChange = {value -> viewModel.updateFoodAlertText(value)},
                           modifier = Modifier.fillMaxWidth()
                       )
                },
                confirmButton = { Text(
                    text = "OK",
                    modifier = Modifier.clickable {
                        viewModel.insertSupplyRequestFood(repo)
                    }
                ) },
                dismissButton = {
                    Text(text = "Cancel", modifier = Modifier.clickable { viewModel.updateShowFoodAlertDialog(false) })
                }
            )
        }

        if (showMedicalAlertDialog.value) {
            AlertDialog(
                onDismissRequest = { viewModel.updateShowMedicalAlertDialog(false) },
                title = { Text(text = "Make Medical Request") },
                text = {
                    OutlinedTextField(
                        value = medicalAlertText.value,
                        onValueChange = {value -> viewModel.updateMedicalAlertText(value)},
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                confirmButton = { Text(
                    text = "OK",
                    modifier = Modifier.clickable {
                        viewModel.insertSupplyRequestMedical(repo)
                    }
                ) },
                dismissButton = {
                    Text(text = "Cancel", modifier = Modifier.clickable { viewModel.updateShowMedicalAlertDialog(false) })
                }
            )
        }

        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (loaded.value) {
                if (viewModel.requestList.isEmpty()) {
                    Text(text = "You haven't done any requests!")
                } else {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(viewModel.requestList) {
                            Card(
                                elevation = CardDefaults.cardElevation(4.dp),
                                modifier = Modifier.padding(5.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    when (it.requestsTable.supplyType) {
                                        0 -> Icon(
                                            imageVector = Icons.Filled.ShoppingCart,
                                            contentDescription = null,
                                            modifier = Modifier.padding(10.dp)
                                        )
                                        1 -> Icon(
                                            imageVector = Icons.Filled.Add,
                                            contentDescription = null,
                                            modifier = Modifier.padding(10.dp)
                                        )
                                    }
                                    Text(
                                        text = it.requestsTable.supplyAmount.toString(),
                                        modifier = Modifier.padding(10.dp),
                                        textAlign = TextAlign.Justify,
                                        style = MaterialTheme.typography.labelLarge
                                    )
                                }
                            }
                        }
                    }
                }
            } else {
                CircularProgressIndicator(modifier = Modifier.size(100.dp))
                Text(text = "Loading Requests Please Wait...")
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun RequestSuppliesScreenPreview() {
    RequestSuppliesScreen()
}