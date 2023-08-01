package com.cse344group10.disastermanagement.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cse344group10.disastermanagement.R
import com.cse344group10.disastermanagement.database.repository.DbRepo
import com.cse344group10.disastermanagement.models.token.SessionToken
import com.cse344group10.disastermanagement.models.user.User
import com.cse344group10.disastermanagement.ui.viewmodels.home.ProfileScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    backAction: () -> Unit = {}
) {
    val repo = DbRepo(LocalContext.current)
    val viewModel: ProfileScreenViewModel = viewModel()

    val showConfirmDialog = viewModel.showConfirmDialog.observeAsState(false)

    val showNameDialog = viewModel.showNameDialog.observeAsState(false)
    val nameDialogName = viewModel.nameDialogName.observeAsState(SessionToken.currentlyLoggedUser!!.userName)
    val nameDialogSurname = viewModel.nameDialogSurname.observeAsState(SessionToken.currentlyLoggedUser!!.userSurname)

    val showEmailDialog = viewModel.showEmailDialog.observeAsState(false)
    val emailDialog = viewModel.emailDialog.observeAsState(SessionToken.currentlyLoggedUser!!.userMail)
    val invalidEmail = viewModel.invalidEmail.observeAsState(false)

    val showPasswordDialog = viewModel.showPasswordDialog.observeAsState(false)
    val currentPassword = viewModel.currentPassword.observeAsState("")
    val newPassword = viewModel.newPassword.observeAsState("")
    val confirmNewPassword = viewModel.confirmNewPassword.observeAsState("")

    val invalidCurrentPassword = viewModel.invalidCurrentPassword.observeAsState(false)
    val invalidConfirmPassword = viewModel.invalidConfirmPassword.observeAsState(false)
    val invalidNewPassword = viewModel.invalidNewPassword.observeAsState(false)

    val passwordVisibility = viewModel.passwordVisibility.observeAsState(false)
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Profile",
                        textAlign = TextAlign.Justify
                    )
                },
                navigationIcon = {
                    IconButton(onClick = backAction) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) {
        if (showConfirmDialog.value) {
            AlertDialog(
                onDismissRequest = { viewModel.updateShowConfirmDialog(false) },
                title = { Text(text = "User has been updated") },
                confirmButton = { Text(
                    text = "OK",
                    modifier = Modifier.clickable { viewModel.updateShowConfirmDialog(false) }
                ) }
            )
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ){
            item {
                Card(
                    modifier = Modifier.padding(all = 5.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "User ID",
                            textAlign = TextAlign.Justify,
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.padding(10.dp)
                        )
                        Text(
                            text = "${SessionToken.currentlyLoggedUser!!.userId}",
                            textAlign = TextAlign.Justify,
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }
            }

            item {
                if (showNameDialog.value) {
                    AlertDialog(
                        onDismissRequest = { viewModel.updateShowNameDialog(false) },
                        title = { Text(
                            text = "Enter new Name-Surname",
                            textAlign = TextAlign.Center
                        ) },
                        text = {
                            Column {
                                OutlinedTextField(
                                    value = nameDialogName.value,
                                    onValueChange = {name -> viewModel.updateNameDialogName(name) },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp)
                                )
                                OutlinedTextField(
                                    value = nameDialogSurname.value,
                                    onValueChange = {surname -> viewModel.updateNameDialogSurname(surname) },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp)
                                )
                            } },
                        confirmButton = { Text(
                            text = "Confirm",
                            modifier = Modifier.clickable { viewModel.updateUserName(repo) }
                        ) },
                        dismissButton = {
                            Text(
                                text = "Cancel",
                                modifier = Modifier.clickable { viewModel.updateShowNameDialog(false) }
                            )
                        }
                    )
                }
                Card(
                    modifier = Modifier.padding(all = 5.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { viewModel.updateShowNameDialog(true) },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Name-Surname",
                            textAlign = TextAlign.Justify,
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.padding(10.dp)
                        )
                        Box(
                            modifier = Modifier.padding(10.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = SessionToken.currentlyLoggedUser!!.userName + " " +
                                            SessionToken.currentlyLoggedUser!!.userSurname,
                                    textAlign = TextAlign.Justify,
                                    style = MaterialTheme.typography.labelLarge,
                                    modifier = Modifier.padding(10.dp)
                                )
                                Icon(
                                    imageVector = Icons.Filled.ArrowForward,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }
            }

            item {
                if (showEmailDialog.value) {
                    AlertDialog(
                        onDismissRequest = { viewModel.updateShowEmailDialog(false) },
                        title = { Text(
                            text = "Enter new e-mail",
                            textAlign = TextAlign.Center
                        ) },
                        text = {
                            OutlinedTextField(
                                value = emailDialog.value,
                                onValueChange = {email -> viewModel.checkUpdateEmail(email) },
                                isError = invalidEmail.value,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)
                            )
                        },
                        confirmButton = {
                            Text(
                                text = "Confirm",
                                modifier = Modifier.clickable { viewModel.updateEmail(repo) }
                            ) },
                        dismissButton = {
                            Text(
                                text = "Cancel",
                                modifier = Modifier.clickable { viewModel.updateShowEmailDialog(false) }
                            )
                        }
                    )
                }
                Card(
                    modifier = Modifier.padding(all = 5.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { viewModel.updateShowEmailDialog(true) },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "E-mail",
                            textAlign = TextAlign.Justify,
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.padding(10.dp)
                        )
                        Box(
                            modifier = Modifier.padding(10.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = SessionToken.currentlyLoggedUser!!.userMail,
                                    textAlign = TextAlign.Justify,
                                    style = MaterialTheme.typography.labelLarge,
                                    modifier = Modifier.padding(10.dp)
                                )
                                Icon(
                                    imageVector = Icons.Filled.ArrowForward,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }
            }

            item {
                if (showPasswordDialog.value) {
                    AlertDialog(
                        onDismissRequest = { viewModel.updateShowPasswordDialog(false) },
                        title = { Text(
                            text = "Change Password",
                            textAlign = TextAlign.Center
                        ) },
                        text = {
                            Column {
                                OutlinedTextField(
                                    value = currentPassword.value,
                                    label = { Text(text = "Current Password") },
                                    onValueChange = { password -> viewModel.checkUpdateCurrentPassword(password) },
                                    visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
                                    isError = invalidCurrentPassword.value,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp),
                                    trailingIcon = {
                                        IconButton(onClick = { viewModel.changePasswordVisibility() }) {
                                            Icon(
                                                imageVector =
                                                if (passwordVisibility.value) ImageVector.vectorResource(id = R.drawable.visibility)
                                                else ImageVector.vectorResource(id = R.drawable.visibilityoff),
                                                contentDescription = null,
                                                modifier = Modifier.size(28.dp)
                                            )
                                        }
                                    }
                                )
                                OutlinedTextField(
                                    value = newPassword.value,
                                    label = { Text(text = "New Password") },
                                    onValueChange = {nPassword -> viewModel.checkUpdateNewPassword(nPassword) },
                                    visualTransformation = PasswordVisualTransformation(),
                                    isError = invalidNewPassword.value,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp)
                                )
                                OutlinedTextField(
                                    value = confirmNewPassword.value,
                                    label = { Text(text = "Confirm New Password") },
                                    onValueChange = {cPassword -> viewModel.checkUpdateConfirmPassword(cPassword) },
                                    visualTransformation = PasswordVisualTransformation(),
                                    isError = invalidConfirmPassword.value,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp)
                                )
                            } },
                        confirmButton = {
                            Text(
                                text = "Confirm",
                                modifier = Modifier.clickable { viewModel.updateUserPassword(repo) }) },
                        dismissButton = {
                            Text(
                                text = "Cancel",
                                modifier = Modifier.clickable {
                                    viewModel.updateShowPasswordDialog(false)
                                    viewModel.checkUpdateCurrentPassword("")
                                    viewModel.checkUpdateNewPassword("")
                                    viewModel.checkUpdateConfirmPassword("")
                                }
                            )
                        }
                    )
                }
                Card(
                    modifier = Modifier.padding(all = 5.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { viewModel.updateShowPasswordDialog(true) },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Password",
                            textAlign = TextAlign.Justify,
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.padding(10.dp)
                        )
                        Icon(
                            imageVector = Icons.Filled.ArrowForward,
                            contentDescription = null,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier.padding(all = 5.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Role",
                            textAlign = TextAlign.Justify,
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.padding(10.dp)
                        )
                        Text(
                            text = when (SessionToken.currentlyLoggedUser!!.userPermissionLevel) {
                                User.PermissionLevel.EmergencySupplyManager -> "Emergency Supply Manager"
                                User.PermissionLevel.AnimalMedicalManager -> "Animal Medical Manager"
                                User.PermissionLevel.HumanMedicalManager -> "Human Medical Manager"
                                User.PermissionLevel.HRManagerSAR -> "Human Resource Manager For SAR"
                                User.PermissionLevel.EquipmentManagerSAR -> "Equipment Manager For SAR"
                                User.PermissionLevel.SystemAdministrator -> "System Administrator"
                                User.PermissionLevel.User -> "User"
                            },
                            textAlign = TextAlign.Justify,
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier.padding(all = 5.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Blood Type",
                            textAlign = TextAlign.Justify,
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.padding(10.dp)
                        )
                        Text(
                            text = when (SessionToken.currentlyLoggedUser!!.userBloodType) {
                                User.BloodType.An -> "A-"
                                User.BloodType.Ap -> "A+"
                                User.BloodType.Bp -> "B+"
                                User.BloodType.Bn -> "B-"
                                User.BloodType.ABp -> "AB+"
                                User.BloodType.ABn -> "AB-"
                                User.BloodType.Zp -> "0+"
                                User.BloodType.Zn -> "0-"
                            },
                            textAlign = TextAlign.Justify,
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier.padding(all = 5.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Gender",
                            textAlign = TextAlign.Justify,
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.padding(10.dp)
                        )
                        Text(
                            text = when (SessionToken.currentlyLoggedUser!!.userGender) {
                                User.Gender.Male -> "Male"
                                User.Gender.Female -> "Female"
                            },
                            textAlign = TextAlign.Justify,
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier.padding(all = 5.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Age",
                            textAlign = TextAlign.Justify,
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.padding(10.dp)
                        )
                        Text(
                            text = SessionToken.currentlyLoggedUser!!.userAge.toString(),
                            textAlign = TextAlign.Justify,
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }
            }
        }
    }


}
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}