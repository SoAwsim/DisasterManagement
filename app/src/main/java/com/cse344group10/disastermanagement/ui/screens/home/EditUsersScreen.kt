package com.cse344group10.disastermanagement.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cse344group10.disastermanagement.database.repository.DbRepo
import com.cse344group10.disastermanagement.models.token.SessionToken
import com.cse344group10.disastermanagement.models.user.User
import com.cse344group10.disastermanagement.ui.viewmodels.home.EditUsersViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditUsersScreen(
    nextPage: () -> Unit = {},
    backAction: () -> Unit = {}
) {

    val repo = DbRepo(LocalContext.current)

    val viewModel : EditUsersViewModel = viewModel()

    val showUsers = viewModel.showUsers.observeAsState(false)

    val selectedCardIndex = viewModel.selectedCardIndex.observeAsState(-1)

    val isDeleteButtonPressed = viewModel.isDeleteButtonPressed.observeAsState(false)
    val isChangePermissionButtonPressed = viewModel.isChangePermissionButtonPressed.observeAsState(false)

    val showUserUpdate = viewModel.showUserUpdate.observeAsState(false)
    val showUserDelete = viewModel.showUserDelete.observeAsState(false)

    val updateExpandDropDownList = viewModel.updateExpandDropDownList.observeAsState(false)
    val selectedDropDownItem = viewModel.selectedDropDownItem.observeAsState("")

    val dropDownIcon = if (updateExpandDropDownList.value) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }

    LaunchedEffect(key1 = true) {
        viewModel.getUsers(repo)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (showUsers.value) {
            Scaffold(
                floatingActionButton = {
                    FloatingActionButton(onClick = nextPage) {
                        Text(
                            text = "Add Employee",
                            modifier=Modifier.padding(horizontal = 5.dp)
                        )
                    }
                },
                topBar = {
                    TopAppBar(
                        title = { Text(text = "Users") },
                        navigationIcon = {
                            IconButton(onClick = backAction) {
                                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                            }
                        }
                    )
                }
            ) {
                if (showUserUpdate.value) {
                    AlertDialog(
                        onDismissRequest = { viewModel.updateShowUserUpdate(false) },
                        confirmButton = {
                            Text(
                                text = "OK",
                                modifier = Modifier.clickable { viewModel.updateShowUserUpdate(false) }
                            ) },
                        title = { Text(text = "User Role Updated")}
                    )
                }
                if (showUserDelete.value) {
                    AlertDialog(
                        onDismissRequest = { viewModel.updateShowUserDelete(false) },
                        confirmButton = {
                            Text(
                                text = "OK",
                                modifier = Modifier.clickable { viewModel.updateShowUserDelete(false) }
                            ) },
                        title = { Text(text = "User Has Been Deleted")}
                    )
                }
                if (isDeleteButtonPressed.value) {
                    AlertDialog(
                        onDismissRequest = { viewModel.updateIsDeleteButtonPressed(false) },
                        title = { Text(text = "Do you really want to delete the User?") },
                        confirmButton = {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "Cancel",
                                    modifier = Modifier.clickable { viewModel.updateIsDeleteButtonPressed(false) }
                                )
                                Text(
                                    text = "Yes",
                                    modifier = Modifier.clickable {
                                        viewModel.updateIsDeleteButtonPressed(false)
                                        viewModel.deleteUser(repo)
                                    }
                                )
                            }
                        }
                    )
                }
                if (isChangePermissionButtonPressed.value) {
                    AlertDialog(
                        onDismissRequest = { viewModel.updateIsChangePermissionButtonPressed(false) },
                        title = { Text(text = "Select New User Role") },
                        confirmButton = {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "Cancel",
                                    modifier = Modifier.clickable { viewModel.updateIsChangePermissionButtonPressed(false) }
                                )
                                Text(
                                    text = "Confirm",
                                    modifier = Modifier.clickable {
                                        viewModel.updateIsChangePermissionButtonPressed(false)
                                        viewModel.updateUser(repo)
                                    }
                                )
                            }
                        },
                        text = {
                            Box(modifier = Modifier.padding(all = 10.dp)) {
                                OutlinedTextField(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            viewModel.changeExpandDropDownList(!updateExpandDropDownList.value)
                                        },
                                    value = selectedDropDownItem.value,
                                    onValueChange = {value -> viewModel.updateSelectedDropDownItem(value) },
                                    trailingIcon = {
                                        Icon(
                                            modifier = Modifier.clickable { viewModel.changeExpandDropDownList(!updateExpandDropDownList.value) },
                                            imageVector = dropDownIcon,
                                            contentDescription = null
                                        )
                                    },
                                    readOnly = true,
                                    enabled = false,
                                    colors = TextFieldDefaults.outlinedTextFieldColors(
                                        disabledTextColor = MaterialTheme.colorScheme.onSurface,
                                        disabledBorderColor = MaterialTheme.colorScheme.outline,
                                        disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                        disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                        disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                        disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                )
                                DropdownMenu(
                                    modifier = Modifier
                                        .width(with(LocalDensity.current) {250.dp}),
                                    expanded = updateExpandDropDownList.value,
                                    onDismissRequest = { viewModel.changeExpandDropDownList(false) }
                                ) {
                                    DropdownMenuItem(
                                        onClick = {
                                            viewModel.updateSelectedDropDownItem("User")
                                            viewModel.changeExpandDropDownList(false) },
                                        text = { Text(text = "User") }
                                    )
                                    DropdownMenuItem(
                                        onClick = {
                                            viewModel.updateSelectedDropDownItem("Emergency Supply Manager")
                                            viewModel.changeExpandDropDownList(false) },
                                        text = { Text(text = "Emergency Supply Manager") }
                                    )
                                    DropdownMenuItem(
                                        onClick = {
                                            viewModel.updateSelectedDropDownItem("Animal Medical Manager")
                                            viewModel.changeExpandDropDownList(false) },
                                        text = { Text(text = "Animal Medical Manager") }
                                    )
                                    DropdownMenuItem(
                                        onClick = {
                                            viewModel.updateSelectedDropDownItem("Human Medical Manager")
                                            viewModel.changeExpandDropDownList(false) },
                                        text = { Text(text = "Human Medical Manager") }
                                    )
                                    DropdownMenuItem(
                                        onClick = { viewModel.updateSelectedDropDownItem("SAR HR Manager")
                                            viewModel.changeExpandDropDownList(false) },
                                        text = { Text(text = "SAR HR Manager") }
                                    )
                                    DropdownMenuItem(
                                        onClick = { viewModel.updateSelectedDropDownItem("SAR Equipment Manager")
                                            viewModel.changeExpandDropDownList(false) },
                                        text = { Text(text = "SAR Equipment Manager") }
                                    )
                                    DropdownMenuItem(
                                        onClick = { viewModel.updateSelectedDropDownItem("System Administrator")
                                            viewModel.changeExpandDropDownList(false) },
                                        text = { Text(text = "System Administrator") }
                                    )
                                }
                            }
                        }
                    )
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it),
                ) {
                    itemsIndexed(viewModel.uiUsers) { index, currentUser ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(all = 5.dp)
                                .clickable {
                                    viewModel.updateSelectedCardIndex(index)
                                },
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                            colors =if (index==selectedCardIndex.value) CardDefaults.cardColors(containerColor = Color.DarkGray)
                            else CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            Row {
                                Icon(
                                    imageVector = if(currentUser.userPermissionLevel==User.PermissionLevel.User) {Icons.Filled.Person}
                                    else {Icons.Filled.Face},
                                    contentDescription = null,
                                    modifier= Modifier.size(70.dp)
                                )

                                Column(modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 5.dp)) {
                                    Text(
                                        text = "User Id: ${currentUser.userId}\n" +
                                                "E-mail: ${currentUser.userMail}\n"
                                    )
                                    if(index == selectedCardIndex.value) {
                                        Row {
                                            Button(
                                                onClick = { viewModel.updateIsChangePermissionButtonPressed(true) },
                                                modifier = Modifier
                                                    .padding(end=50.dp)

                                            ) {
                                                Text(
                                                    text = "Change Permission"
                                                )
                                            }
                                            if (index + 1 != SessionToken.currentlyLoggedUser!!.userId) {
                                                Button(
                                                    onClick = { viewModel.updateIsDeleteButtonPressed(true) },
                                                    colors= ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onErrorContainer)
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Filled.Delete,
                                                        contentDescription = null,
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.padding(40.dp))
                    }
                }
            }


        } else {
            CircularProgressIndicator(
                modifier = Modifier.size(100.dp)
            )
            Text(
                modifier = Modifier.padding(10.dp),
                text = "Generating User Data Please Wait..",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun EditUserPagePreview() {
    EditUsersScreen {}
}

