package com.cse344group10.disastermanagement.ui.screens.loading

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.cse344group10.disastermanagement.models.user.User
import com.cse344group10.disastermanagement.ui.viewmodels.loading.UserLoadingScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserLoadingScreen(
    nextPage : () -> Unit
) {
    BackHandler {
        // do nothing when back button is pressed
    }
    val repo = DbRepo(LocalContext.current)

    val viewModel : UserLoadingScreenViewModel = viewModel()

    val showUsers = viewModel.showUsers.observeAsState(false)

    LaunchedEffect(key1 = true) {
        viewModel.populateUserDatabase(repo)
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
                        Text(text = "Next")
                    }
                }
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it),
                ) {
                    item {
                        Text(
                            text = "7 Users Has Been Generated At The Start",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                    items(viewModel.uiUsers) {currentUser ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(all = 5.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Column(modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    text = "User Id: ${currentUser.userId}\n" +
                                            "Name/Surname: ${currentUser.userName} ${currentUser.userSurname}\n" +
                                            "E-mail: ${currentUser.userMail}\n" +
                                            "Password: ${currentUser.userPassword}\n" +
                                            "User Role: ${when (currentUser.userPermissionLevel) {
                                                User.PermissionLevel.EmergencySupplyManager -> "Emergency Supply Manager"
                                                User.PermissionLevel.AnimalMedicalManager -> "Animal Medical Supply Manager"
                                                User.PermissionLevel.HumanMedicalManager -> "Human Medical Supply Manager"
                                                User.PermissionLevel.HRManagerSAR -> "SAR Human Resource Manager"
                                                User.PermissionLevel.EquipmentManagerSAR -> "SAR Vehicle Manager"
                                                User.PermissionLevel.SystemAdministrator -> "System Administrator"
                                                else -> "Normal User"
                                            }}\n" +
                                            "User Blood Type: ${when (currentUser.userBloodType) {
                                                User.BloodType.An -> "A-"
                                                User.BloodType.Bp -> "B+"
                                                User.BloodType.Bn -> "B-"
                                                User.BloodType.ABp -> "AB+"
                                                User.BloodType.ABn -> "AB-"
                                                User.BloodType.Zp -> "0+"
                                                User.BloodType.Zn -> "0-"
                                                else -> "A+"
                                            }
                                            }\n" +
                                            "User House Id: ${currentUser.userHouse.buildingId}\n" +
                                            "User Gender: ${when (currentUser.userGender) {
                                                User.Gender.Female -> "Female"
                                                else -> "Male"
                                            }
                                            }\n" +
                                            "User Age: ${currentUser.userAge}\n" +
                                            "User Security Question Type: ${currentUser.userSecurityQuestion.question}\n" +
                                            "User Security Question Answer: ${currentUser.userSecurityQuestionAnswer}"
                                )
                            }
                        }
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
private fun UserLoadingScreenPreview() {
    UserLoadingScreen {}
}