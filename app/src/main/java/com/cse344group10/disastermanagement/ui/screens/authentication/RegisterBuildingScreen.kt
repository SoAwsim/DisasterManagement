@file:JvmName("RegisterPage3Kt")

package com.cse344group10.disastermanagement.ui.screens.authentication


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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cse344group10.disastermanagement.database.repository.DbRepo
import com.cse344group10.disastermanagement.models.map.Building
import com.cse344group10.disastermanagement.models.map.EmergencyDepot
import com.cse344group10.disastermanagement.models.map.MedicalSupplyDepot
import com.cse344group10.disastermanagement.models.map.SarCenter
import com.cse344group10.disastermanagement.ui.viewmodels.authentication.RegisterBuildingViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterBuildingScreen(
    mail: String,
    password: String,
    name: String,
    surname: String,
    age: Int,
    gender: Int,
    blood: Int,
    question: Int,
    answer: String,
    notes: String,
    nextPage: () -> Unit = {},
    chooseRole: Boolean = false
) {
    val viewModel : RegisterBuildingViewModel = viewModel()

    val showProgress = viewModel.showProgress.observeAsState(false)
    val expandDropDownList = viewModel.expandDropDownList.observeAsState(false)
    val selectedDropDownItem = viewModel.selectedDropDownItem.observeAsState("Sector 0")
    val textFilledSize = viewModel.textFilledSize.observeAsState(Size.Zero)
    val dropDownIcon = if (expandDropDownList.value) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }



    val showChooseRoleDialog = viewModel.showChooseRoleDialog.observeAsState(false)
    val showAlertDialog = viewModel.showAlertDialog.observeAsState(false)
    val userCreatedDialog = viewModel.userCreatedDialog.observeAsState(false)

    val repo = DbRepo(LocalContext.current)

    val selectedCardIndex = viewModel.selectedCardIndex.observeAsState(-1)
    val isNextButtonEnabled = viewModel.isNextButtonEnabled.observeAsState(false)

    val selectedEmployeeRole = viewModel.selectedEmployeeRole.observeAsState("Emergency Supply Manager")
    val employeeRoleDropDown = viewModel.employeeRoleDropDown.observeAsState(false)

    val employeeDropDownIcon = if (employeeRoleDropDown.value) {
        Icons.Filled.KeyboardArrowDown
    } else {
        Icons.Filled.KeyboardArrowDown
    }

    LaunchedEffect(key1 = true) {
        viewModel.populateBuildingDataBase(repo)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (showProgress.value) {
            Text(
                text = "Select the building you are living in",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(10.dp)
            )
            Box(modifier = Modifier.padding(all = 10.dp)) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.updateExpandDropList(!expandDropDownList.value) }
                        .onGloballyPositioned {
                            viewModel.updateTextFilledSize(it.size.toSize())
                        },
                    value = selectedDropDownItem.value,
                    onValueChange = { viewModel.updateSelectedDropDownItem(it) },
                    trailingIcon = {
                        Icon(
                            modifier = Modifier.clickable { viewModel.updateExpandDropList(!expandDropDownList.value) },
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
                        .width(with(LocalDensity.current) {textFilledSize.value.width.toDp()}),
                    expanded = expandDropDownList.value,
                    onDismissRequest = { viewModel.updateExpandDropList(false) }
                ) {
                    viewModel.dropDownList.forEach { label ->
                        DropdownMenuItem(
                            onClick = { viewModel.dropDownMenuItemSelect(label) },
                            text = { Text(text = label) }
                        )
                    }
                }
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(5)
            ) {
                items(viewModel.buildingUiElements) { currentBuilding ->
                    Card(
                        modifier = if (
                            currentBuilding !is EmergencyDepot &&
                            currentBuilding !is MedicalSupplyDepot &&
                            currentBuilding !is SarCenter
                        ) {
                            Modifier
                                .padding(all = 10.dp)
                                .size(50.dp)
                                .clickable {
                                    viewModel.updatedSelectedCardIndex(currentBuilding.buildingId)
                                    viewModel.updateNextButtonEnabled(true)
                                    viewModel.alertDialogBuilding = currentBuilding
                                }
                        }else{
                            Modifier
                                .padding(all = 10.dp)
                                .size(50.dp)
                             },
                        colors = if (selectedCardIndex.value == currentBuilding.buildingId) CardDefaults.cardColors(containerColor = Color.Red)
                        else CardDefaults.cardColors(containerColor =MaterialTheme.colorScheme.surfaceVariant ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)

                    ) {
                        val imageVector : ImageVector = when (currentBuilding) {
                            is EmergencyDepot -> Icons.Filled.Menu
                            is MedicalSupplyDepot -> Icons.Filled.ThumbUp
                            is SarCenter -> Icons.Filled.Warning
                            else -> Icons.Filled.Home
                        }
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = imageVector,
                                contentDescription = null
                            )
                        }
                    }
                }
            }

            if (showAlertDialog.value) {
                AlertDialog(
                    onDismissRequest = { viewModel.alertDialogHide() },
                    title = { Text(text = "BuildingId = ${viewModel.alertDialogBuilding.buildingId}") },

                    text = {
                        Column {
                            AlertDialogText(alertDialogBuilding = viewModel.alertDialogBuilding)
                            Spacer(modifier = Modifier.padding(vertical=10.dp))
                            Text(
                                text = "Do you confirm that the building whose information is given above is the building you reside in?",
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                color=Color.Green
                            )
                        }
                    },

                    confirmButton = {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        )
                        {
                            Text(
                                modifier = Modifier.clickable { viewModel.alertDialogHide() },
                                text = "CANCEL",
                                color = Color.Red,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                modifier=Modifier
                                    .clickable{
                                        if (!chooseRole) {
                                            viewModel.addUser(
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
                                                repo = repo
                                            )
                                        } else {
                                            viewModel.alertDialogHide()
                                            viewModel.updateShowChooseRoleDialog(true)
                                        } }
                                ,text="YES",
                                color=Color.Blue,
                                fontWeight = FontWeight.Bold

                            )
                        }
                    }
                )
            }

            if (showChooseRoleDialog.value) {
                AlertDialog(
                    onDismissRequest = { viewModel.updateShowChooseRoleDialog(false) },
                    confirmButton = {
                        Text(
                            text = "OK",
                            modifier = Modifier.clickable {
                                viewModel.addUser(
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
                                    repo = repo,
                                    permission = when (selectedEmployeeRole.value) {
                                        "Animal Medical Manager" -> 2
                                        "Human Medical Manager" -> 3
                                        "SAR HR Manager" -> 4
                                        "SAR Equipment Manager" -> 5
                                        "System Administrator" -> 6
                                        else -> 1
                                    }
                                )
                                viewModel.updateShowChooseRoleDialog(false)
                            }
                        )
                    },
                    dismissButton = {
                        Text(
                            text = "Cancel",
                            modifier = Modifier.clickable { viewModel.updateShowChooseRoleDialog(false) }
                        )
                    },
                    title = { Text(text = "Choose Employee Role") },
                    text = {
                        Box(modifier = Modifier.padding(10.dp)) {
                            OutlinedTextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { viewModel.updateEmployeeRoleDropDown(!employeeRoleDropDown.value) },
                                value = selectedEmployeeRole.value,
                                onValueChange = { viewModel.updateSelectedEmployeeRole(it) },
                                trailingIcon = {
                                    Icon(
                                        imageVector = employeeDropDownIcon,
                                        contentDescription = null,
                                        modifier = Modifier.clickable { viewModel.updateEmployeeRoleDropDown(!employeeRoleDropDown.value) }
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
                                modifier = Modifier.width(with(LocalDensity.current) {250.dp}),
                                expanded = employeeRoleDropDown.value,
                                onDismissRequest = { viewModel.updateEmployeeRoleDropDown(false) }
                            ) {
                                DropdownMenuItem(
                                    onClick = {
                                        viewModel.updateSelectedEmployeeRole("Emergency Supply Manager")
                                        viewModel.updateEmployeeRoleDropDown(false) },
                                    text = { Text(text = "Emergency Supply Manager") }
                                )
                                DropdownMenuItem(
                                    onClick = {
                                        viewModel.updateSelectedEmployeeRole("Animal Medical Manager")
                                        viewModel.updateEmployeeRoleDropDown(false) },
                                    text = { Text(text = "Animal Medical Manager") }
                                )
                                DropdownMenuItem(
                                    onClick = {
                                        viewModel.updateSelectedEmployeeRole("Human Medical Manager")
                                        viewModel.updateEmployeeRoleDropDown(false) },
                                    text = { Text(text = "Human Medical Manager") }
                                )
                                DropdownMenuItem(
                                    onClick = { viewModel.updateSelectedEmployeeRole("SAR HR Manager")
                                        viewModel.updateEmployeeRoleDropDown(false) },
                                    text = { Text(text = "SAR HR Manager") }
                                )
                                DropdownMenuItem(
                                    onClick = { viewModel.updateSelectedEmployeeRole("SAR Equipment Manager")
                                        viewModel.updateEmployeeRoleDropDown(false) },
                                    text = { Text(text = "SAR Equipment Manager") }
                                )
                                DropdownMenuItem(
                                    onClick = { viewModel.updateSelectedEmployeeRole("System Administrator")
                                        viewModel.updateEmployeeRoleDropDown(false) },
                                    text = { Text(text = "System Administrator") }
                                )
                            }
                        }
                    }
                )
            }

            if (userCreatedDialog.value) {
                AlertDialog(
                    confirmButton = { Text(
                        text = "OK",
                        modifier = Modifier.clickable { nextPage() }
                    )},
                    title = { Text(text = "User Created Successfully")},
                    onDismissRequest = { nextPage() }
                )
            }

            Button(
                onClick = {viewModel.alertDialogShow(viewModel.alertDialogBuilding)},
                enabled = isNextButtonEnabled.value
            ) {
                Text(text = "Next")
            }
        } else {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(100.dp)
                    .padding(all = 10.dp)
            )
            Text(
                modifier = Modifier.padding(10.dp),
                text = "Generating Map Data Please Wait..",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun AlertDialogText(alertDialogBuilding: Building) {
    when(alertDialogBuilding) {
        is EmergencyDepot -> {
            Text(
                text = "Building Year = ${alertDialogBuilding.buildingYear}\n" +
                        "Building Type = Emergency Supply Depot\n" +
                        "Building Durability = ${alertDialogBuilding.buildingDurability}\n" +
                        "Building I Coordinate = ${alertDialogBuilding.buildingI}\n" +
                        "Building J Coordinate = ${alertDialogBuilding.buildingJ}\n" +
                        "Emergency Supply Depot Id = ${alertDialogBuilding.emergencyInfoId}\n" +
                        "Food Count = ${alertDialogBuilding.foodCount}\n" +
                        "Shelter Count = ${alertDialogBuilding.shelterCount}\n" +
                        "Transport Vehicle Count = ${alertDialogBuilding.transportVehicleCount}\n" +
                        "Util Count = ${alertDialogBuilding.utilCount}\n" +
                        "Baby Supply Count = ${alertDialogBuilding.babySupplyCount}\n" +
                        "Liquid Amount = ${alertDialogBuilding.liquidAmount}\n" +
                        "Water Amount = ${alertDialogBuilding.waterAmount}\n" +
                        "Toilet Count = ${alertDialogBuilding.toiletCount}\n" +
                        "Power Supply Count = ${alertDialogBuilding.powerSupplyCount}"
            )
        }
        is MedicalSupplyDepot -> {
            Text(
                text = "Building Year = ${alertDialogBuilding.buildingYear}\n" +
                        "Building Type = Medical Center\n" +
                        "Building Durability = ${alertDialogBuilding.buildingDurability}\n" +
                        "Building I Coordinate = ${alertDialogBuilding.buildingI}\n" +
                        "Building J Coordinate = ${alertDialogBuilding.buildingJ}\n" +
                        "Medical Center Id = ${alertDialogBuilding.medicalInfoId}\n" +
                        "Medicine Animal A = ${alertDialogBuilding.medicineAnimalACount}\n" +
                        "Medicine Animal B = ${alertDialogBuilding.medicineAnimalBCount}\n" +
                        "Medicine Animal C = ${alertDialogBuilding.medicineAnimalCCount}\n" +
                        "Medicine Human A = ${alertDialogBuilding.medicineHumanACount}\n" +
                        "Medicine Human B = ${alertDialogBuilding.medicineHumanBCount}\n" +
                        "Medicine Human C = ${alertDialogBuilding.medicineHumanCCount}"
            )
        }
        is SarCenter -> {
            Text(
                text = "Building Year = ${alertDialogBuilding.buildingYear}\n" +
                        "Building Type = SAR Center\n" +
                        "Building Durability = ${alertDialogBuilding.buildingDurability}\n" +
                        "Building I Coordinate = ${alertDialogBuilding.buildingI}\n" +
                        "Building J Coordinate = ${alertDialogBuilding.buildingJ}\n" +
                        "SAR Center Id = ${alertDialogBuilding.sarInfoId}\n" +
                        "SAR Human Count = ${alertDialogBuilding.sarHumanCount}\n" +
                        "SAR Vehicle Count = ${alertDialogBuilding.sarVehicleCount}"
            )
        }
        else -> {
            Text(
                text = "Building Year = ${alertDialogBuilding.buildingYear}\n" +
                        "Building Resident Count = ${alertDialogBuilding.residentCount}\n" +
                        "Building Type = Normal Building\n" +
                        "Building Durability = ${alertDialogBuilding.buildingDurability}\n" +
                        "Building I Coordinate = ${alertDialogBuilding.buildingI}\n" +
                        "Building J Coordinate = ${alertDialogBuilding.buildingJ}"
            )
        }
    }

}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun RegisterBuildingScreenPreview() {
    RegisterBuildingScreen (
        mail = "",
        password = "",
        name = "",
        surname = "",
        age = 0,
        gender = 0,
        blood = 0,
        question = 0,
        answer = "",
        notes = ""
    )
}